package de.mrlauchi.gsplrg_connectpaper.acerace.Listeners

import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class AceRaceRespawnListener : Listener {

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        if(AceRaceEssentials.getActive() == 1) {
            val player = event.player
            event.respawnLocation = AceRaceEssentials.getCoordinate(AceRaceEssentials.getSection(player))

        }
    }

}