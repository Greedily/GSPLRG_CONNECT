package de.mrlauchi.gsplrg_connectpaper.tgttos.listener

import de.mrlauchi.gsplrg_connectpaper.tgttos.other.Essentials
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class DeathListener : Listener {

    @EventHandler
    fun onDeath(event: PlayerRespawnEvent) {
        val player: Player = event.player
        if(Essentials.getActive() != null) {
            if(Essentials.getActive() == "PZ" || Essentials.getActive() == "ww1" || Essentials.getActive() == "th") {
                event.respawnLocation = Essentials.getSpawnLoc(Essentials.getActive()!!)
            }
        }

    }

}