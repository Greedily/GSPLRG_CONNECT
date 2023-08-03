package de.mrlauchi.gsplrg_connectpaper.skywars.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class SkywarsMoveListener : Listener {
    @EventHandler
    fun onMove(event: PlayerMoveEvent){
        val config = Main.instance!!.config
        if(config.getInt("skywars.countdownactive") == 1) {
            if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
                event.isCancelled = true
            }
        }
    }
}