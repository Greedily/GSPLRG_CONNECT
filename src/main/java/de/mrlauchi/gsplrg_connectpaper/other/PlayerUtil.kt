package de.mrlauchi.gsplrg_connectpaper.other

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

object PlayerUtil {

    fun heal(player: Player) {
        player.saturation = 20.0F
        player.foodLevel = 20
        player.saturation = 20.0F
    }

    fun resetVisVul(player: Player) {
        player.isInvisible = false
        player.isInvulnerable = false
    }

    fun setVisVul(player: Player) {
        player.isInvisible = true
        player.isInvulnerable = true
    }

    fun clear(player: Player) {
        player.inventory.clear()
    }

    fun adventure(player: Player) {
        player.gameMode = GameMode.ADVENTURE
    }

    fun spec(player: Player) {
        player.gameMode = GameMode.SPECTATOR
    }

}