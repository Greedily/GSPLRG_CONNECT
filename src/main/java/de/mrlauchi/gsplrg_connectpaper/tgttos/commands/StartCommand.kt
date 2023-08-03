package de.mrlauchi.gsplrg_connectpaper.tgttos.commands

import de.mrlauchi.gsplrg_connectpaper.tgttos.other.Countdown
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("op")) {
            if(sender is Player) {
                val player = sender.player
                if(args.size == 1) {
                    if(args[0] == "ww1") {
                        Countdown.countdown(player!!, args[0])
                    }
                    if(args[0] == "pz") {
                        Countdown.countdown(player!!, args[0])
                    }
                }
            }
        }
        return false
    }

}