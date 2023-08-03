package de.mrlauchi.gsplrg_connectpaper.skywars.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object SkywarsEssentials {

    fun setCoords(player: Player, team : String){

        val config = Main.instance!!.config

        config.set("skywars.spawn.$team.x", player.location.x)
        config.set("skywars.spawn.$team.y", player.location.y)
        config.set("skywars.spawn.$team.z", player.location.z)

        config.set("skywars.spawn.$team.world", player.location.world!!.name)

        config.set("skywars.mapspawn.x", -108.520)
        config.set("skywars.mapspawn.y", 49.0)
        config.set("skywars.mapspawn.z", -1780.435)

        Main.instance!!.saveConfig()
        player.sendMessage("SetSpawn Success")
        return
    }

    fun getCoords(team: String): Location {
        val config = Main.instance!!.config
        val x = config.getDouble("skywars.spawn.$team.x")
        val y = config.getDouble("skywars.spawn.$team.y")
        val z = config.getDouble("skywars.spawn.$team.z")

        val worldName = config.getString("skywars.spawn.$team.world")
        val world = Bukkit.getWorld(worldName!!)
        return Location(world, x, y, z)
    }

    fun setGameModeEnabled(value: Boolean){
        val config = Main.instance!!.config

        if (value){ // Enable Game Mode
            config.set("skywars.gamemodeactive",1)
        }else{
            config.set("skywars.gamemodeactive",0)

            Bukkit.getWorld("world")!!.setGameRuleValue("keepInventory", "true")
            Bukkit.getWorld("world")!!.setGameRuleValue("fallDamage", "false") //
        }
        Main.instance!!.saveConfig()
    }

    fun setCountDownEnabled(value: Boolean){
        val config = Main.instance!!.config

        if (value){ // Enable Countdown
            config.set("skywars.countdownactive",1)
        }else{
            config.set("skywars.countdownactive",0)
        }
        Main.instance!!.saveConfig()
    }
}