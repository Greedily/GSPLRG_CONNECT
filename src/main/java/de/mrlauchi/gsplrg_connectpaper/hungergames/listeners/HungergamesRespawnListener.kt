package de.mrlauchi.gsplrg_connectpaper.hungergames.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class HungergamesRespawnListener : Listener {
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent){
        val config = Main.instance!!.config
        if (config.getInt("hungergames.gamemodeactive") == 1){
            val x = config.getDouble("hungergames.mapspawn.x")
            val y = config.getDouble("hungergames.mapspawn.y")
            val z = config.getDouble("hungergames.mapspawn.z")

            event.respawnLocation = Location(Bukkit.getWorld("world"),x,y,z)
        }
    }
}