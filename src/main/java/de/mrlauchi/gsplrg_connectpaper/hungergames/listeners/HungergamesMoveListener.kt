package de.mrlauchi.gsplrg_connectpaper.hungergames.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class HungergamesMoveListener : Listener {
    @EventHandler
    fun onMove(event: PlayerMoveEvent){
        val config = Main.instance!!.config
        if(config.getInt("hungergames.countdownactive") == 1) {
            if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
                event.isCancelled = true
            }
        }
    }
}