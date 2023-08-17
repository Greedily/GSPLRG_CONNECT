package de.mrlauchi.gsplrg_connectpaper.PublicListeners

import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.inventory.PlayerInventory

class ItemMoveEvent: Listener {
    @EventHandler
    fun ondrop(event: InventoryClickEvent){
        if (AceRaceEssentials.getActive() == 1 || ParkourEssentials.getActive() == 1 || RocketSpleefEssentials.getActive() == 1){
           event.isCancelled = true

        }
    }
}