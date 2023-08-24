package de.mrlauchi.gsplrg_connectpaper.other.voting.commands

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.voting.other.VotingEssentials
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VoteCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        val config = Main.instance!!.config
        if (args.size == 1){
            if (args[0] == "start" && sender.isOp()){
                Bukkit.broadcastMessage("-----------------------\nVoting Started!!\n Use /vote to vote!\n-----------------------")
                config.set("voting.active",1)
                Bukkit.broadcastMessage("${VotingEssentials.currenttab} ${VotingEssentials.playersvoted}")
                for (plr in Bukkit.getOnlinePlayers()){
                    VotingEssentials.currenttab.plus(Pair(plr.name, "ACERACE"))
                }
            }
            if (args[0] == "stop" && sender.isOp()){
                Bukkit.broadcastMessage("-----------------------\nVoting Ended!!\n-----------------------")
                config.set("voting.active",0)
                VotingEssentials.returnallvotes()
                VotingEssentials.resetvotes()
            }

        }else{
            if (config.getInt("voting.active") == 1){
                val inventory = Bukkit.createInventory(sender, 54, "§f七七七七七七七七\uEA00") //§f


                if (VotingEssentials.playersvoted.contains(sender.name)){
                    inventory.setItem(31, VotingEssentials.returnitem(Material.MAP, 1010, "ALREADY VOTED"))
                }else{
                    inventory.setItem(31, VotingEssentials.returnitem(Material.MAP, 1010, "VOTE!"))

                    inventory.setItem(45, VotingEssentials.returnitem(Material.MAP, 1, "ACERACE"))
                    inventory.setItem(46, VotingEssentials.returnitem(Material.MAP, 3, "DEATHMATCH"))
                    inventory.setItem(47, VotingEssentials.returnitem(Material.MAP, 5, "HUNGERGAMES"))
                    inventory.setItem(48, VotingEssentials.returnitem(Material.MAP, 7, "ROCKETSPLEEF"))
                    inventory.setItem(49, VotingEssentials.returnitem(Material.MAP, 9, "PARKOUR"))
                    inventory.setItem(50, VotingEssentials.returnitem(Material.MAP, 11, "SKYWARS"))
                    inventory.setItem(51, VotingEssentials.returnitem(Material.MAP, 13, "GOLDRUSH"))
                    inventory.setItem(52, VotingEssentials.returnitem(Material.MAP, 15, "To Get To The Other Side(TGTTOS)"))
                }

                sender.openInventory(inventory)
            }else{
                sender.sendMessage("Voting isnt Enabled yet!")
            }
        }

        return false
    }
}