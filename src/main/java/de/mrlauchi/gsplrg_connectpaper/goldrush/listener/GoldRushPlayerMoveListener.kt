package de.mrlauchi.gsplrg_connectpaper.goldrush.listener

import de.mrlauchi.gsplrg_connectpaper.goldrush.other.GoldRushEssentials
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class GoldRushPlayerMoveListener : Listener {
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if(GoldRushEssentials.getCountdownActive() == 1) {
            if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
                event.isCancelled = true
            }
        }
    }

}