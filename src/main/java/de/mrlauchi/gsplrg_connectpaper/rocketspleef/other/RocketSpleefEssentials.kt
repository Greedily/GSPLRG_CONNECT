package de.mrlauchi.gsplrg_connectpaper.rocketspleef.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object RocketSpleefEssentials {
    fun setGameActive(value : Boolean){
        val config = Main.instance!!.config
        if (value) {
            config.set("rocketspleef.gamemodeactive",1)
        }else{
            config.set("rocketspleef.gamemodeactive",0)
        }
        Main.instance!!.saveConfig()
    }
    fun getGameActive() : Int{
        val config = Main.instance!!.config
        return config.getInt("rocketspleef.gamemodeactive")
    }
    fun setCountdownActive(value : Boolean){
        val config = Main.instance!!.config
        if (value) {
            config.set("rocketspleef.countdownactive",1)
        }else{
            config.set("rocketspleef.countdownactive",0)
        }
        Main.instance!!.saveConfig()
    }
    fun getCountdownActive() : Int{
        val config = Main.instance!!.config
        return config.getInt("rocketspleef.countdownactive")
    }
    fun  setMapSpawn(name: String, player: Player){
        val config = Main.instance!!.config
        config.set("rocketspleef.maps.$name.x", player.location.x)
        config.set("rocketspleef.maps.$name.y", player.location.y)
        config.set("rocketspleef.maps.$name.z", player.location.z)
        Main.instance!!.saveConfig()
    }

    fun getMapSpawn(name : String) : Location{
        val config = Main.instance!!.config
        val x = config.getDouble("rocketspleef.maps.$name.x")
        val y = config.getDouble("rocketspleef.maps.$name.y")
        val z = config.getDouble("rocketspleef.maps.$name.z")

        return Location(Bukkit.getWorld("world"),x,y,z)
    }
}