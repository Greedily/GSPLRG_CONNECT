package de.mrlauchi.gsplrg_connectpaper.PublicListeners

import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryMoveItemEvent

class ItemMoveEvent: Listener {
    @EventHandler
    fun ondrop(event: InventoryMoveItemEvent){
        if (AceRaceEssentials.getActive() == 1 || ParkourEssentials.getActive() == 1 || RocketSpleefEssentials.getActive() == 1){
            event.isCancelled = true
        }
    }
}