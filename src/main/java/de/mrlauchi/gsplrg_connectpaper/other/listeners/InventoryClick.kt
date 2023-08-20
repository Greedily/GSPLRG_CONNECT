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
        val voteinventorytitle = "§f七七七七七七七七\uEA00"
        if (event.view.title == voteinventorytitle){
            val inventory = event.view.topInventory
            if (event.currentItem?.type == Material.BROWN_STAINED_GLASS_PANE){
                if (event.currentItem?.itemMeta?.displayName == "VOTED") {
                    event.isCancelled = true
                    return
                }
                inventory.remove(Material.BROWN_STAINED_GLASS_PANE)
                event.view.title = ""
                val VOTEDitem = ItemStack(Material.BROWN_STAINED_GLASS_PANE)
                val meta = VOTEDitem.itemMeta
                meta.setDisplayName("VOTED")
                VOTEDitem.setItemMeta(meta)

                inventory.setItem(31, VOTEDitem)
                Bukkit.broadcastMessage("${event.view.player.name} VOTED!")
                event.isCancelled = true
            }
        }

        if (AceRaceEssentials.getActive() == 1 || ParkourEssentials.getActive() == 1){
            if (event.view.player.gameMode == GameMode.ADVENTURE){
                event.isCancelled = true
            }
        }
    }
}