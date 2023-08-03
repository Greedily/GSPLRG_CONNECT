package de.mrlauchi.gsplrg_connectpaper.goldrush.listener

import de.mrlauchi.gsplrg_connectpaper.goldrush.other.GoldRushEssentials
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class GoldRushRespawnListener : Listener {

    @EventHandler
    fun onRespawn(event : PlayerRespawnEvent) {
        if(GoldRushEssentials.isActive()) {
            event.respawnLocation = GoldRushEssentials.getCoords(event.player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(event.player.name))!!.name)
        }
    }

}