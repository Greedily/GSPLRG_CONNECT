package de.mrlauchi.gsplrg_connectpaper.parkour.listener

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class ParkourRespawnListener : Listener {

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        if(ParkourEssentials.getActive() == 1) {
            val player = event.player

            event.respawnLocation = ParkourEssentials.getCoordinate(ParkourEssentials.getSection(player))
        }
    }

}