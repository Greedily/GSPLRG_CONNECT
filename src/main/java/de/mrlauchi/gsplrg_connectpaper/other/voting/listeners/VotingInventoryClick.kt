package de.mrlauchi.gsplrg_connectpaper.other.voting.listeners

import de.mrlauchi.gsplrg_connectpaper.other.voting.other.VotingEssentials
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class VotingInventoryClick : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent){
        val voteinventorytitle = "§f七七七七七七七七\uEA00"
        if (event.view.title == voteinventorytitle){
            val clickedItemMeta = event.currentItem?.itemMeta
            val hasCustomModelData = clickedItemMeta?.hasCustomModelData()
            val customModelData = clickedItemMeta?.customModelData
            val player : Player = event.view.player as Player
            val inventory = event.view.topInventory
            if (event.currentItem?.type == Material.MAP && hasCustomModelData == true && customModelData == 1010){ // voted

                if(VotingEssentials.playersvoted.contains(player.name)){
                    player.sendMessage("You Already Voted!")
                }else{
                    VotingEssentials.votefunction(player)
                    inventory.clear()
                    inventory.setItem(31, VotingEssentials.returnitem(Material.MAP, 1010, "VOTED"))
                    Bukkit.broadcastMessage("${event.view.player.name} VOTED!")
                }

            }

            if(event.currentItem?.type == Material.MAP && hasCustomModelData == true && customModelData == 1){ // acerace
                VotingEssentials.onitemclick(Material.TRIDENT, inventory, player)

            }
            if(event.currentItem?.type == Material.MAP && customModelData == 3){ // deathmatch
                VotingEssentials.onitemclick(Material.CROSSBOW, inventory, player)

            }
            if(event.currentItem?.type == Material.MAP && customModelData == 5){ // hungergames
                VotingEssentials.onitemclick(Material.GOLDEN_SWORD, inventory, player)

            }
            if(event.currentItem?.type == Material.MAP && customModelData == 7){ // rocket spleef
                VotingEssentials.onitemclick(Material.FIRE_CHARGE, inventory, player)

            }
            if(event.currentItem?.type == Material.MAP && customModelData == 9 ){ // parkour
                VotingEssentials.onitemclick(Material.LEATHER_BOOTS, inventory, player)

            }
            if(event.currentItem?.type == Material.MAP && customModelData == 11){ // skywars
                VotingEssentials.onitemclick(Material.IRON_SWORD, inventory, player)

            }
            if(event.currentItem?.type == Material.MAP && customModelData == 13){ // gold rush
                VotingEssentials.onitemclick(Material.GOLD_INGOT, inventory, player)

            }
            if(event.currentItem?.type == Material.MAP && customModelData == 15){ // tgttos
                VotingEssentials.onitemclick(Material.IRON_BOOTS, inventory, player)

            }
            event.isCancelled = true
        }
    }
}