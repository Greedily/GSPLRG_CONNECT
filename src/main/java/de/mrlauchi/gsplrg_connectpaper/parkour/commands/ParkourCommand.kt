package de.mrlauchi.gsplrg_connectpaper.parkour.commands

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class ParkourCommand : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("op")) {
            if(sender is Player) {
                val player = sender.player!!

                if(args.isNotEmpty()) {
                    if(args[0] == "start") {
                        de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourCountdown.countdown(player)
                    }
                    if(args[0] == "stop") {
                        de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourStopCommand.stop(player)
                        //ParkourEssentials.stopTimer()

                    }
                    if(args[0] == "setsection") {
                        if(args.size >= 2) {
                            ParkourEssentials.setCoordinate(args[1].toInt(), player)
                        }
                    }
                }
            }
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        val tabComplete: MutableList<String> = ArrayList()
        tabComplete.add("start")
        tabComplete.add("stop")
        tabComplete.add("setsection")

        return tabComplete
    }
}