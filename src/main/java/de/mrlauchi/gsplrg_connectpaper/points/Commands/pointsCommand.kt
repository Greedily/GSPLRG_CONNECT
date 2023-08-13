package de.mrlauchi.gsplrg_connectpaper.points.Commands

import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class pointsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("op")) {
            if(sender is Player) {
                val player = sender.player!!

                if(args.isNotEmpty()) {
                    if (args.size > 2) {
                        if (args[0] == "setpoints") {
                            for (player in Bukkit.getOnlinePlayers()) {
                                if (player.name == args[1]) {
                                    pointsEssentials.setplayerpoints(player, args[2].toInt())
                                }
                            }
                        }
                        if (args[0] == "addpoints") {
                            for (player in Bukkit.getOnlinePlayers()) {
                                if (player.name == args[1]) {
                                    pointsEssentials.addplayerpoints(player, args[2].toInt())
                                }
                            }
                        }

                        if (args[0] == "removepoints") {
                            for (player in Bukkit.getOnlinePlayers()) {
                                if (player.name == args[1]) {
                                    pointsEssentials.removeplayerpoints(player, args[2].toInt())
                                }
                            }
                        }
                    }
                    if (args[0] == "resetallpoints") {
                        pointsEssentials.resetallplayerpoints()
                    }
                    if (args[0] == "updateteampoints") {
                        pointsEssentials.updateteampoints()
                    }



                }
            }
        }else{
            if(sender is Player) {
                sender.sendMessage("Your Points: " + pointsEssentials.getplayerpoints(sender) + "Your Team Points: " + pointsEssentials.getteampoints(sender))
            }
        }


        return false
    }
}