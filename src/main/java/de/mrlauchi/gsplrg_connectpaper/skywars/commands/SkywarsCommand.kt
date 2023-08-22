package de.mrlauchi.gsplrg_connectpaper.skywars.commands

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.ParticleEssentials
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.skywars.other.SkywarsCountDown
import de.mrlauchi.gsplrg_connectpaper.skywars.other.SkywarsEssentials
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Item
import org.bukkit.entity.Player

class SkywarsCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (sender.isOp()) { // /skywars setspawn <team>
            if (args.isNotEmpty()) {
                if (args[0] == "setspawn"){
                    if (args.size >= 2) {

                        SkywarsEssentials.setCoords(sender,args[1])
                    }

                }
                // /skywars start
                if (args[0] == "start"){//start countdown, tp all players respectivly(in countdown func)
                    SkywarsEssentials.setGameModeEnabled(true) // for death listeners
                    SkywarsEssentials.setCountDownEnabled(true) // for beginning countdown
                    //start the countdown
                    SkywarsCountDown.countdown(sender)

                    sender.sendMessage("Starting Skywars...")

                    for (entity in Bukkit.getWorld("world")!!.entities) {
                        if(entity is Item) {
                            entity.remove()
                        }
                    }

                }
                if (args[0] == "stop"){
                    SkywarsEssentials.setCountDownEnabled(false) // stop the countdown
                    SkywarsEssentials.setGameModeEnabled(false) // stop the death listener
                    val x = Main.instance!!.config.getDouble("skywars.mapspawn.x")
                    val y = Main.instance!!.config.getDouble("skywars.mapspawn.y")
                    val z = Main.instance!!.config.getDouble("skywars.mapspawn.z")
                    PasteSchem.paste(Location(Bukkit.getWorld("world"), x, y, z),"skywarsmap1")
                    sender.sendMessage("Game Stopped.")
                    Spawn.teleport(true)
                }
            }
        }
        return  false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        val tabComplete: MutableList<String> = ArrayList()
        tabComplete.add("start")
        tabComplete.add("stop")
        tabComplete.add("setspawn")

        return tabComplete
    }

}

