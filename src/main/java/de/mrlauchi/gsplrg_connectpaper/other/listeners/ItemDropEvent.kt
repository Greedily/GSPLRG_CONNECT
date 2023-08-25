package de.mrlauchi.gsplrg_connectpaper.other.listeners

import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import de.mrlauchi.gsplrg_connectpaper.deathmatch.other.DeathmatchEssentials
import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropEvent: Listener {
    @EventHandler
    fun ondrop(event: PlayerDropItemEvent){
        if (RocketSpleefEssentials.getActive() == 1 || AceRaceEssentials.getActive() == 1 || ParkourEssentials.getActive() == 1 || DeathmatchEssentials.getActive() == 1){
            if (event.player.gameMode == GameMode.ADVENTURE){
                event.isCancelled = true
            }
        }
    }
}