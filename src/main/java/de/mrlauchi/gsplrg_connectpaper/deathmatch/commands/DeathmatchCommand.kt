package de.mrlauchi.gsplrg_connectpaper.deathmatch.commands

import de.mrlauchi.gsplrg_connectpaper.deathmatch.other.DeathmatchCountdown
import de.mrlauchi.gsplrg_connectpaper.deathmatch.other.DeathmatchEssentials
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class DeathmatchCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (sender.isOp()){
            if (args.isNotEmpty()) {
                if (args[0] == "setspawn"){
                    if (args.size >= 2) {
                        DeathmatchEssentials.setCoords(sender, args[1])
                    }
                }
                if (args[0] == "start"){
                    DeathmatchEssentials.setGamemodeEnabled(true)
                    DeathmatchCountdown.countdown(sender)
                    Bukkit.broadcastMessage("starting deathmatch!")
                }
                if (args[0] == "stop"){
                    DeathmatchEssentials.Stop()
                    sender.sendMessage("Stopping deathmatch!")

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