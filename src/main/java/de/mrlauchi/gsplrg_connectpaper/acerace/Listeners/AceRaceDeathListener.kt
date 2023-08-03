package de.mrlauchi.gsplrg_connectpaper.acerace.Listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class AceRaceDeathListener : Listener {

    @EventHandler
    fun onDeath(event : PlayerDeathEvent) {
        val config = Main.instance!!.config
        if(config.getInt("acerace.gamemodeactive") == 1) {
            event.deathMessage = null
            event.entity.sendMessage("§cYou died!")
        }
    }

}