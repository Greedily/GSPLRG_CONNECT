package de.mrlauchi.gsplrg_connectpaper.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.GameMode

object Spawn {

    fun teleport(adventure : Boolean) {
        val config = Main.instance!!.config

        if(config.getInt("event.status") == 1) {
            for (player in Bukkit.getOnlinePlayers()) {
                //if(player.scoreboard.getTeam("admin")!!.hasPlayer(player)) return
                player.teleport(Bukkit.getWorld("world")!!.spawnLocation)
                if (adventure) {
                    player.gameMode = GameMode.ADVENTURE
                }
                return
            }
        }
    }

}