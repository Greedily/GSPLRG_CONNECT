package de.mrlauchi.gsplrg_connectpaper.parkour.listener

import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class ParkourDamageListener : Listener {

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Firework) {
            val fw = event.damager as Firework
            if (fw.customName == "nodamage") {
                event.isCancelled = true
            }
        }
    }

}