package de.mrlauchi.gsplrg_connectpaper.acerace.Commands

import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceCountDown
import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class AceRaceCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (sender.isOp()) {
            if (args.isNotEmpty()) {
                if (args[0] == "start"){
                    AceRaceEssentials.setGameModeEnabled(true)
                    AceRaceEssentials.setCountDownEnabled(true)
                    AceRaceCountDown.countdown(sender)
                }
                if (args[0] == "setsection"){
                    if(args.size >= 2) {
                        AceRaceEssentials.setCoordinate(args[1].toInt(), sender)
                    }
                }
                if (args[0] == "setspawn") {
                    AceRaceEssentials.setStartCoords(sender)
                }
                if (args[0] == "stop"){
                    AceRaceEssentials.setGameModeEnabled(false)
                    AceRaceEssentials.setCountDownEnabled(false)
                    AceRaceEssentials.stopTimer()
                    for (target in Bukkit.getOnlinePlayers()) {
                        target.inventory.clear()
                        target.gameMode = GameMode.SPECTATOR
                    }
                }
                if (args[0] == "setelytra"){
                    if (args.size > 1) {
                        if (args[1] == "add") {

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
        tabComplete.add("setstart")

        return tabComplete
    }
}