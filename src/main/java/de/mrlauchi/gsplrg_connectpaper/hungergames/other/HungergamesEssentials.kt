package de.mrlauchi.gsplrg_connectpaper.hungergames.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object HungergamesEssentials {

    var currentteamplacement = 0

    var endmsg = listOf<String?>()


    var teams = listOf<String>()

    var deadpeeps = listOf<String>()
    fun setCoords(player: Player, team : String){

        val config = Main.instance!!.config

        config.set("hungergames.spawn.$team.x", player.location.x)
        config.set("hungergames.spawn.$team.y", player.location.y)
        config.set("hungergames.spawn.$team.z", player.location.z)

        config.set("hungergames.spawn.$team.world", player.location.world!!.name)

        config.set("hungergames.mapspawn.x", -255.500) //-255.500 / 25.875 / -1032.403 ////237.573, 133.86, 75.013
        config.set("hungergames.mapspawn.y", 25.875)
        config.set("hungergames.mapspawn.z", -1032.403)

        Main.instance!!.saveConfig()
        player.sendMessage("SetSpawn Success")
        return
    }

    fun getCoords(team: String): Location {
        val config = Main.instance!!.config
        val x = config.getDouble("hungergames.spawn.$team.x")
        val y = config.getDouble("hungergames.spawn.$team.y")
        val z = config.getDouble("hungergames.spawn.$team.z")

        val worldName = config.getString("hungergames.spawn.$team.world")
        val world = Bukkit.getWorld("world")
        return Location(world, x, y, z)
    }

    fun setGameModeEnabled(value : Boolean){
        val config = Main.instance!!.config
        if (value) {
            config.set("hungergames.gamemodeactive",1)

            for (target in Bukkit.getOnlinePlayers()) {
                val targetteam = target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
                if (!teams.contains(targetteam)) { // add all the alive teams into the list.
                   currentteamplacement +=1
                    teams += targetteam
                }
            }

        }else{
            config.set("hungergames.gamemodeactive",0)
            resetplacements()
            Bukkit.getWorld("world")!!.setGameRuleValue("keepInventory", "true")
            Bukkit.getWorld("world")!!.setGameRuleValue("fallDamage", "false") //
        }
        Main.instance!!.saveConfig()
    }

    fun setCountDownEnabled(value: Boolean){
        val config = Main.instance!!.config

        if (value){ // Enable Countdown
            config.set("hungergames.countdownactive",1)
        }else{
            config.set("hungergames.countdownactive",0)
        }
        Main.instance!!.saveConfig()
    }

    fun setteamPlacement(team : String){
        val config = Main.instance!!.config
        var tem = team.toUpperCase()
        if (currentteamplacement < 10){
            if (currentteamplacement == 1){
                endmsg += " §l${currentteamplacement}st:§r §6${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 2){
                endmsg += " §l${currentteamplacement}nd:§r §9${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 3){
                endmsg += " §l${currentteamplacement}rd:§r §a${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement > 3){
                endmsg += " §l${currentteamplacement}th:§r ${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
        }else{
            if (currentteamplacement != 21 && currentteamplacement != 22 && currentteamplacement != 23){
                endmsg += " §l${currentteamplacement}th:§r ${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 21){
                endmsg += " §l${currentteamplacement}st:§r ${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 22){
                endmsg += " §l${currentteamplacement}nd:§r ${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 23){
                endmsg += " §l${currentteamplacement}rd:§r ${tem}§r (${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
        }

        currentteamplacement -= 1
    }
    fun resetplacements(){
        endmsg = listOf<String?>()
        currentteamplacement = 0
        teams = listOf<String>()
        deadpeeps = listOf<String>()
    }
}