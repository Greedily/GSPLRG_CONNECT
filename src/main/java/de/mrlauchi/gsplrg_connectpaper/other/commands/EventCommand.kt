package de.mrlauchi.gsplrg_connectpaper.other.commands

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EventCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender !is Player) return true
        if(!sender.isOp) return true;

        if (args != null) {
            if(args.size >= 2) {
                if(args[0] == "setStatus") {
                    val config = Main.instance!!.config

                    sender.sendMessage("Set the event status to: ${args[1]}")

                    config.set("event.status", args[1].toInt())
                    Main.instance!!.saveConfig()
                }
            }
            if(args[0] == "status") {
                val config = Main.instance!!.config
                sender.sendMessage("The event status is: ${config.getInt("event.status")}")
            }
        }

        return true
    }
}