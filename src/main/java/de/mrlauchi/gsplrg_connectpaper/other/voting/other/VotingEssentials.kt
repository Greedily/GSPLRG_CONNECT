package de.mrlauchi.gsplrg_connectpaper.other.voting.other

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object VotingEssentials {
    var currenttab = mutableMapOf<String, String>()

    var playersvoted = mutableListOf<String>()

    var gamevotes = mutableMapOf<String, Int>("ACERACE" to 0,
        "DEATHMATCH" to 0,
        "HUNGERGAMES" to 0,
        "ROCKETSPLEEF" to 0,
        "PARKOUR" to 0,
        "SKYWARS" to 0,
        "GOLDRUSH" to 0,
        "To Get To The Other Side(TGTTOS)" to 0)

    val allitems = mapOf<Material, String>(Material.TRIDENT to "ACERACE",
            Material.CROSSBOW to "DEATHMATCH",
            Material.GOLDEN_SWORD to "HUNGERGAMES",
            Material.FIRE_CHARGE to "ROCKETSPLEEF",
            Material.LEATHER_BOOTS to "PARKOUR",
            Material.IRON_SWORD to "SKYWARS",
            Material.GOLD_INGOT to "GOLDRUSH",
            Material.IRON_BOOTS to "To Get To The Other Side(TGTTOS)")

    val allitemsslots = mapOf<Material, Int>(Material.TRIDENT to 45,//e
        Material.CROSSBOW to 46,
        Material.GOLDEN_SWORD to 47,
        Material.FIRE_CHARGE to 48,
        Material.LEATHER_BOOTS to 49,
        Material.IRON_SWORD to 50,
        Material.GOLD_INGOT to 51,
        Material.IRON_BOOTS to 52)

    val itemsdeselected = mapOf<Material, Int>(Material.TRIDENT to 1,
            Material.CROSSBOW to 3,
            Material.GOLDEN_SWORD to 5,
            Material.FIRE_CHARGE to 7,
            Material.LEATHER_BOOTS to 9,
            Material.IRON_SWORD to 11,
            Material.GOLD_INGOT to 13,
            Material.IRON_BOOTS to 15
    )

    val itemsselected = mapOf<Material, Int>(Material.TRIDENT to 2,
            Material.CROSSBOW to 4,
            Material.GOLDEN_SWORD to 6,
            Material.FIRE_CHARGE to 8,
            Material.LEATHER_BOOTS to 10,
            Material.IRON_SWORD to 12,
            Material.GOLD_INGOT to 14,
            Material.IRON_BOOTS to 16)

    fun returnitem(material : Material, CustomModelData : Int, Name : String): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        meta.setCustomModelData(CustomModelData)
        meta.setDisplayName(Name)
        item.setItemMeta(meta)
        return item
    }

    fun onitemclick(clickeditem : Material, inventory : Inventory, player : Player){
        if (playersvoted.contains(player.name)) return
        inventory.clear()
        for (item in allitems){
            if (item.key != clickeditem){
                inventory.setItem(allitemsslots[item.key]!!,VotingEssentials.returnitem(Material.MAP, itemsdeselected[item.key] as Int, item.value))
            }else{
                inventory.setItem(allitemsslots[item.key]!!,VotingEssentials.returnitem(Material.MAP, itemsselected[clickeditem] as Int, item.value))

                currenttab[player.name] = item.value
            }
        }
        inventory.setItem(31, returnitem(Material.MAP, 1010, "VOTE!"))
    }


    fun votefunction(plr : Player){
        val plrtab = currenttab[plr.name]!!
        for (game in gamevotes){
            if (game.key == plrtab){

                val oldvalue : Int = game.value
                val gamevalue = game.key
                val newvalue = oldvalue + 1
                gamevotes.replace(gamevalue, oldvalue, newvalue)
                playersvoted += plr.name
            }
        }
    }

    fun returnallvotes(){
        var descendingvotes = mutableMapOf<Int, String>()
        for (element in gamevotes){
            descendingvotes.put(element.value, element.key)
        }
        descendingvotes = descendingvotes.toSortedMap(Comparator.reverseOrder())
        val games = descendingvotes.values.toList()
        val votes = descendingvotes.keys.toList()
        Bukkit.broadcastMessage(descendingvotes.toString())
        Bukkit.broadcastMessage("-----------------\nALL GAME VOTES:\n")
        Bukkit.broadcastMessage("§6CHOSEN: ${games[0]} with ${votes[0]} Votes.\n \n§r Some of The Other Votes:")
        for(element in descendingvotes){
            val votes = element.key
            val game = element.value
            if (element.value != games[0]){
                Bukkit.broadcastMessage("\n${game} with §6${votes}§r Votes!")
            }
        }
        Bukkit.broadcastMessage("\n-----------------")
    }


    fun resetvotes(){
        currenttab = mutableMapOf()
        for (game in gamevotes){
            val gameval = game.key

           gamevotes.replace(gameval, 0)
        }
        playersvoted = mutableListOf<String>()
    }


}