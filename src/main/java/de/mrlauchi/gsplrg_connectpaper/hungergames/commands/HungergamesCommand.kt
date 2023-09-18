package de.mrlauchi.gsplrg_connectpaper.hungergames.commands

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.hungergames.other.HungerGamesTime
import de.mrlauchi.gsplrg_connectpaper.hungergames.other.HungergamesEssentials
import de.mrlauchi.gsplrg_connectpaper.hungergames.other.HungergamesCountdown
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Item
import org.bukkit.entity.Player

class HungergamesCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (sender.isOp()){
            if (args.isNotEmpty()) {
                if (args[0] == "setspawn"){
                    if (args.size >= 2) {

                        HungergamesEssentials.setCoords(sender,args[1])
                    }

                }

                if (args[0] == "start"){//start countdown, tp all players respectivly(in countdown func)
                    Bukkit.broadcastMessage("yes")
                    HungergamesEssentials.setGameModeEnabled(true) // for death listeners
                    HungergamesEssentials.setCountDownEnabled(true) // for beginning countdown
                    //start the countdown
                    HungergamesCountdown.countdown(sender)
                    //start the game time
                    HungerGamesTime.StartGameTime()

                    sender.sendMessage("Starting Hungergames...")

                    for (entity in Bukkit.getWorld("world")!!.entities) {
                        if(entity is Item) {
                            entity.remove()
                        }
                    }

                }
                if (args[0] == "stop"){
                    HungergamesEssentials.setCountDownEnabled(false) // stop the countdown
                    HungergamesEssentials.setGameModeEnabled(false) // stop the death listener
                    val config = Main.instance!!.config
                    val x = config.getDouble("hungergames.mapspawn.x")
                    val y = config.getDouble("hungergames.mapspawn.y")
                    val z = config.getDouble("hungergames.mapspawn.z")

                    PasteSchem.paste(Location(Bukkit.getWorld("world"), x, y, z),"hungergamesmap1")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"worldborder set 1000000")
                    sender.sendMessage("Game Stopped.")

                    Spawn.teleport(true)
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