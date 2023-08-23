package de.mrlauchi.gsplrg_connectpaper.other.listeners

import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class InventoryClick : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent){
        if (AceRaceEssentials.getActive() == 1 || ParkourEssentials.getActive() == 1){
            if (event.view.player.gameMode == GameMode.ADVENTURE){
                event.isCancelled = true
            }
        }
    }
}