package de.mrlauchi.gsplrg_connectpaper.parkour.commands

import de.mrlauchi.gsplrg_connectpaper.other.PlayerUtil
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object ParkourStopCommand {

    fun stop(player: Player) {
        ParkourEssentials.setActive(0)
        ParkourEssentials.resetPlacementsString()
        Spawn.teleport(true)
        for (target in Bukkit.getOnlinePlayers()) {
            PlayerUtil.resetVisVul(target)
            PlayerUtil.clear(target)

            ParkourEssentials.setSection(target, 0)
        }
    }

}