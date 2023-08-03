package de.mrlauchi.gsplrg_connectpaper.parkour.listener

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class ParkourDeathListener : Listener {

    @EventHandler
    fun onDeath(event : PlayerDeathEvent) {
        if(ParkourEssentials.getActive() == 1) {
            event.deathMessage = null
            event.entity.sendMessage("§cYou died!")
        }
    }

}