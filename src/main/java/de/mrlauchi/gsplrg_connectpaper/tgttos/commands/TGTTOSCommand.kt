package de.mrlauchi.gsplrg_connectpaper.tgttos.commands

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.pz.pz
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.th.th
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.ww1.ww1
import de.mrlauchi.gsplrg_connectpaper.tgttos.other.Countdown
import de.mrlauchi.gsplrg_connectpaper.tgttos.other.Essentials
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player


class TGTTOSCommand : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("op")) {
            if(sender is Player) {
                val player = sender.player
                if(args.isNotEmpty()) {
                    val location = player!!.location
                    if(args[0] == "setspawn") {
                        if(args.size >= 2) {
                            if(args[1] == "ww1") {
                                ww1.setSpawn(player!!)
                            }
                            if(args[1] == "pz") {
                                pz.setSpawn(player!!)
                            }
                            if(args[1] == "th") {
                                th.setSpawn(player!!)
                            }
                        }
                    }
                    if(args[0] == "setend") {
                        if(args.size >= 2) {
                            if(args[1] == "ww1") {
                                ww1.setEnd(player!!)
                            }
                            if(args[1] == "pz") {
                                pz.setEnd(player!!)
                            }
                            if(args[1] == "th") {
                                th.setEnd(player!!)
                            }
                        }
                    }
                    if(args[0] == "start") {
                        if(args.size >= 2) {
                            if(args[1] == "ww1") {
                                Countdown.countdown(player, args[1])
                            }
                            if(args[1] == "pz") {
                                Countdown.countdown(player, args[1])
                            }
                            if(args[1] == "th") {
                                Countdown.countdown(player, args[1])
                            }
                        }
                    }
                    if(args[0] == "stop") {
                        if(args.size == 2) {
                            if(args[1] == "ww1") {
                                ww1.stop(player)
                                Essentials.resetplacements()
                            }
                            if(args[1] == "pz") {
                                pz.stop(player)
                                Essentials.resetplacements()
                            }
                            if(args[1] == "th") {
                                th.stop(player)
                                Essentials.resetplacements()
                            }
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
        tabComplete.add("setend")
        tabComplete.add("setspawn")

        return tabComplete
    }
}