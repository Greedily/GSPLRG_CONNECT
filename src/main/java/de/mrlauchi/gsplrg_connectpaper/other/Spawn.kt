package de.mrlauchi.gsplrg_connectpaper.other

import org.bukkit.Bukkit

object Spawn {

    fun teleport() {
        for (player in Bukkit.getOnlinePlayers()) {
            player.teleport(Bukkit.getWorld("world")!!.spawnLocation)
        }
    }

}