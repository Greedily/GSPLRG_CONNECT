package de.mrlauchi.gsplrg_connectpaper.goldrush.commands

import de.mrlauchi.gsplrg_connectpaper.goldrush.other.GoldRushCountdown
import de.mrlauchi.gsplrg_connectpaper.goldrush.other.GoldRushEssentials
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GoldRushCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (sender.isOp()) {

            if (args.isNotEmpty()) {
                //start command
                if (args[0] == "start") {
                    GoldRushCountdown.countdown(sender)

                }
                if (args[0] == "stop") {
                    StopCommand.stop()
                }
                if (args[0] == "setspawn") {
                    if (args.size >= 2) {
                        GoldRushEssentials.setCoords(sender,args[1]) // /goldrush setspawn(args[0]) <team>(args[1])
                    }
                }
                if (args[0] == "resetmap") {
                    if (args.size >= 2) {
                        GoldRushEssentials.setCoords(sender,args[1]) // /goldrush setspawn(args[0]) <team>(args[1])
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
        tabComplete.add("setspawn")

        return tabComplete
    }
}