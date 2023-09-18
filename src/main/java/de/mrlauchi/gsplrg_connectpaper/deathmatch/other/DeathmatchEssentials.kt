package de.mrlauchi.gsplrg_connectpaper.deathmatch.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object DeathmatchEssentials {
    val config = Main.instance!!.config

    var streaks = mutableMapOf<String , Int>()

    var currentplacement  = 0
    var endmsg = listOf<String?>()
    fun setGamemodeEnabled(value : Boolean){
        if (value){
            config.set("deathmatch.gamemodeactive",1)
            for (plr in Bukkit.getOnlinePlayers()){
                streaks += Pair(plr.name, 0)
            }
        }else{
            config.set("deathmatch.gamemodeactive",0)
            streaks.clear()
            for (plr in Bukkit.getOnlinePlayers()) {
                plr.maxHealth = 20.0
                plr.health = 20.0
            }
        }
        Main.instance!!.saveConfig()
    }

    fun getActive() : Int{
        return config.getInt("deathmatch.gamemodeactive")
    }

    fun setCountDownEnabled(value : Boolean){
        if (value == true){
            config.set("deathmatch.countdownactive",1)
        }else{
            config.set("deathmatch.countdownactive",0)
        }
        Main.instance!!.saveConfig()
    }

    fun setCoords(player: Player, team : String){

        config.set("deathmatch.spawn.$team.x", player.location.x)
        config.set("deathmatch.spawn.$team.y", player.location.y)
        config.set("deathmatch.spawn.$team.z", player.location.z)

        config.set("deathmatch.spawn.$team.world", player.location.world!!.name)

        Main.instance!!.saveConfig()
        player.sendMessage("SetSpawn Success")
        return
    }

    fun getCoords(team: String): Location {
        val x = config.getDouble("deathmatch.spawn.$team.x")
        val y = config.getDouble("deathmatch.spawn.$team.y")
        val z = config.getDouble("deathmatch.spawn.$team.z")

        val worldName = config.getString("deathmatch.spawn.$team.world")
        val world = Bukkit.getWorld(worldName!!)
        return Location(world, x, y, z)
    }

    fun waitTimeUntillPlayergetdmged(int : Int, target: Player){
        var time = int
        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                if (time <= 0 && config.getInt("deathmatch.gamemodeactive") == 1) {
                    target.gameMode = GameMode.SURVIVAL
                    target.isInvulnerable = false
                    target.sendMessage("You are now vulnerable")
                    this.cancel()
                }
                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)
    }

    fun giveplayerstreak(player : Player){
        val oldvalue = streaks[player.name] as Int
        streaks.replace(player.name,oldvalue, oldvalue + 1)
    }

    fun resetplayerstreak(player : Player){
        val oldvalue = streaks[player.name] as Int
        streaks.replace(player.name, oldvalue, 0)
    }

    fun resetallkills(){
        for (plr in Bukkit.getOnlinePlayers()) {
            config.set("deathmatch.score.${plr.name}", 0)
        }
        Main.instance!!.saveConfig()
    }

    fun getPlacements(nameslist : List<String> , pointslist : List<Int>){
        val config = Main.instance!!.config

        for (name in nameslist) {
            val kills = pointslist[nameslist.indexOf(name)]

            currentplacement += 1
            if (currentplacement < 10){
                if (currentplacement == 1){
                    endmsg += " §l${currentplacement}st:§r §6${name}§r with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
                if (currentplacement == 2){
                    endmsg += " §l${currentplacement}nd:§r §9${name}§r with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
                if (currentplacement == 3){
                    endmsg += " §l${currentplacement}rd:§r §a${name}§r with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
                if (currentplacement > 3){
                    endmsg += " §l${currentplacement}th:§r ${name} with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
            }else{
                if (currentplacement != 21 && currentplacement != 22 && currentplacement != 23){
                    endmsg += " §l${currentplacement}th:§r ${name} with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
                if (currentplacement == 21){
                    endmsg += " §l${currentplacement}st:§r ${name} with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
                if (currentplacement == 22){
                    endmsg += " §l${currentplacement}nd:§r ${name} with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
                if (currentplacement == 23){
                    endmsg += " §l${currentplacement}rd:§r ${name} with ${kills} kills(${kills * pointsModule.deathmatch.kill} points)"
                }
            }
        }

        for (msg in endmsg) {
            Bukkit.broadcastMessage(msg as String)
        }

    }

    fun resetPlacementsString(){
        endmsg = listOf()
        currentplacement = 0
    }
    fun Stop() {
        for (target in Bukkit.getOnlinePlayers()) {
            target.inventory.clear()
            target.sendTitle("DEATHMATCH ENDS!", "")
        }

        setGamemodeEnabled(false)
        setCountDownEnabled(false)
        resetallkills()
        resetPlacementsString()
        Spawn.teleport(true)
        pointsEssentials.updateteampoints()
    }

}