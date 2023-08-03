package de.mrlauchi.gsplrg_connectpaper.deathmatch.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class DeathmatchMoveListener : Listener {
    @EventHandler
    fun onMove(event: PlayerMoveEvent){
        val config = Main.instance!!.config
        if(config.getInt("deathmatch.countdownactive") == 1) {
            if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
                event.isCancelled = true
            }
        }
    }
}