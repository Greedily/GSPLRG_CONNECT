package de.mrlauchi.gsplrg_connectpaper.deathmatch.other

import de.mrlauchi.gsplrg_connectpaper.Main
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
    fun setGamemodeEnabled(value : Boolean){
        if (value == true){
            config.set("deathmatch.gamemodeactive",1)
            for (plr in Bukkit.getOnlinePlayers()){
                streaks.plus(Pair(plr.name, 0))
            }
        }else{
            config.set("deathmatch.gamemodeactive",0)
            streaks.clear()
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
        player.sendMessage(streaks[player.name].toString())
        val oldvalue = streaks[player.name] as Int
        streaks.replace(player.name,oldvalue, oldvalue + 1)
    }

    fun resetplayerstreak(player : Player){
        val oldvalue = streaks[player.name] as Int
        streaks.replace(player.name, oldvalue, 0)
    }

}