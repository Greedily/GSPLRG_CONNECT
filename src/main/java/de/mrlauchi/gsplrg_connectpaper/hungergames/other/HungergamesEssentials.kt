package de.mrlauchi.gsplrg_connectpaper.hungergames.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object HungergamesEssentials {

    var currentplacement = 24

    var endmsg = listOf<String?>()
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

    fun setGameModeEnabled(value: Boolean){
        val config = Main.instance!!.config

        if (value){ // Enable Game Mode
            config.set("hungergames.gamemodeactive",1)
        }else{
            config.set("hungergames.gamemodeactive",0)

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

    fun setPlacement(player : Player){
        val config = Main.instance!!.config
        //24th
        currentplacement -= 1
        if (currentplacement < 10){
            if (currentplacement == 1){
                endmsg += " §l${currentplacement}st:§r §6${player.name}§r (with ${pointsModule.hungergames.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 2){
                endmsg += " §l${currentplacement}nd:§r §9${player.name}§r (with ${pointsModule.hungergames.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 3){
                endmsg += " §l${currentplacement}rd:§r §a${player.name}§r (with ${pointsModule.hungergames.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement > 3){
                endmsg += " §l${currentplacement}th:§r ${player.name} (with ${pointsModule.hungergames.placementlist[currentplacement]} extra points)"
            }
        }else{
            if (currentplacement != 21 && currentplacement != 22 && currentplacement != 23){
                endmsg += " §l${currentplacement}th:§r ${player.name} (with ${pointsModule.hungergames.placementlist[currentplacement]} extra point)"
            }
            if (currentplacement == 21){
                endmsg += " §l${currentplacement}st:§r ${player.name} (with ${pointsModule.hungergames.placementlist[currentplacement]} extra point)"
            }
            if (currentplacement == 22){
                endmsg += " §l${currentplacement}nd:§r ${player.name} (with ${pointsModule.hungergames.placementlist[currentplacement]} extra point)"
            }
            if (currentplacement == 23){
                endmsg += " §l${currentplacement}rd:§r ${player.name} (with ${pointsModule.hungergames.placementlist[currentplacement]} extra point)"
            }

        }
    }
    fun resetplacements(){
        endmsg = listOf<String?>()
        currentplacement = 24
    }
}