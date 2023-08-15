package de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener

import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class RocketSpleefDropListener : Listener {

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        if(RocketSpleefEssentials.getActive() == 1) {
            event.isCancelled = true
        }
    }

}