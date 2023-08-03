package de.mrlauchi.gsplrg_connectpaper.tgttos.commands

import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.pz.pz
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.ww1.ww1
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetEndCommand : CommandExecutor{

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("op")) {
            if(sender is Player) {
                val player = sender as Player
                if(args.size == 1) {
                    if(args[0] == "ww1") {
                        ww1.setEnd(player)
                    }
                    if(args[0] == "pz") {
                        pz.setEnd(player)
                    }
                }
            }
        }
        return false
    }

}