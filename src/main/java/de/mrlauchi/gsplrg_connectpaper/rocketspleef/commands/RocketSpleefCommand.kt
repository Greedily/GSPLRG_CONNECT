package de.mrlauchi.gsplrg_connectpaper.rocketspleef.commands

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefCountdown
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class RocketSpleefCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (!sender.isOp()) return false
        if (args[0] == "start") {
            if (args.size > 1) {
                RocketSpleefCountdown.countdown(sender, args[1])
            }else{
                sender.sendMessage("you need to specify the map name you want to start the game in!")
            }
        }
        if (args[0] == "setmapspawn"){
            if (args.size > 1) {
                RocketSpleefEssentials.setMapSpawn(args[1],sender)
            }else{
                sender.sendMessage("you need to specify the map name you want to set the Spawn coordinates in!")
            }
        }
        if (args[0] == "stop") {
            RocketSpleefEssentials.setGameActive(false)
            RocketSpleefEssentials.setCountdownActive(false)
            for (target in Bukkit.getOnlinePlayers()) {
                target.gameMode = GameMode.SPECTATOR
                target.inventory.clear()
                target.isInvisible = false
            }
            val map = Main.instance!!.config.getString("rocketspleef.currentmap")
            PasteSchem.paste(Location(Bukkit.getWorld("world"),-385.508, 109.0, -2055.485), map)
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        val tabComplete: MutableList<String> = ArrayList()
        tabComplete.add("start")
        tabComplete.add("stop")
        tabComplete.add("setmapspawn")

        return tabComplete
    }
}