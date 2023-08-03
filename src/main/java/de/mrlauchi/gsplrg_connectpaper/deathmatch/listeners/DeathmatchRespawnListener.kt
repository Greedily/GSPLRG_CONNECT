package de.mrlauchi.gsplrg_connectpaper.deathmatch.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.deathmatch.other.DeathmatchEssentials
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class DeathmatchRespawnListener : Listener {
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent){
        val config = Main.instance!!.config
        if (config.getInt("deathmatch.gamemodeactive") == 1) {
            val target = event.player
            val location = DeathmatchEssentials.getCoords(target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name)

            event.respawnLocation = location
            target.isInvulnerable = true
            target.sendMessage("cant take dmg")
            Bukkit.broadcastMessage("${target.name} is Invulnerable")
            DeathmatchEssentials.waitTimeUntillPlayergetdmged(3, target)

        }

    }
}