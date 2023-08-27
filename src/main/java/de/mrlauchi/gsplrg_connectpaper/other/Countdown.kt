package de.mrlauchi.gsplrg_connectpaper.other

import org.bukkit.event.player.PlayerMoveEvent

object Countdown {

    fun freeze(event: PlayerMoveEvent) {
        if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
            event.isCancelled = true
        }
    }

}