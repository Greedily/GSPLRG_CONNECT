package de.mrlauchi.gsplrg_connectpaper.parkour.commands

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object ParkourStopCommand {

    fun stop(player: Player) {
        ParkourEssentials.setActive(0)
        for (target in Bukkit.getOnlinePlayers()) {
            target.isInvulnerable = false
            target.isInvisible = false

            ParkourEssentials.setSection(target, 0)
            // finish timer

        }
    }

}