package de.mrlauchi.gsplrg_connectpaper.parkour.commands

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourCountdown
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object ParkourStartCommand {

    fun start(player : Player) {
        ParkourCountdown.countdown(player)
    }

}