package de.mrlauchi.gsplrg_connectpaper.other.commands

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class VoteCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        val config = Main.instance!!.config
        if (args.size == 1){
            if (args[0] == "start" && sender.isOp()){
                Bukkit.broadcastMessage("-----------------------\nVoting Started!!\n-----------------------")
                config.set("voting.active",1)
            }
            if (args[0] == "stop" && sender.isOp()){
                Bukkit.broadcastMessage("-----------------------\nVoting Ended!!\n-----------------------")
                config.set("voting.active",0)
            }

        }else{
            if (config.getInt("voting.active") == 1){
                val inventory = Bukkit.createInventory(sender, 54, "§f七七七七七七七七\uEA00") //§f

                val VOTEitem = ItemStack(Material.BROWN_STAINED_GLASS_PANE)
                val meta = VOTEitem.itemMeta
                meta.setDisplayName("VOTE")
                VOTEitem.setItemMeta(meta)

                inventory.setItem(31, VOTEitem)

                inventory.setItem(50, ItemStack(Material.TRIDENT))

                sender.openInventory(inventory)
            }
        }

        return false
    }
}