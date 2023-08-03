package de.mrlauchi.gsplrg_connectpaper.skywars.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class SkywarsRespawnListener : Listener {
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent){ // maybe also change gamerule keep inv to false when its started and to false when stopped???? maybe also make fall damage true  yes.
        val config = Main.instance!!.config
        if (config.getInt("skywars.gamemodeactive") == 1) {

            val x = config.getDouble("skywars.mapspawn.x")
            val y = config.getDouble("skywars.mapspawn.y")
            val z = config.getDouble("skywars.mapspawn.z")

            event.respawnLocation = Location(Bukkit.getWorld("world"),x, y, z) // -108.0, 84.0, -1780.0
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"tp ${event.player.name} $x $y $z")
        }

    }
}