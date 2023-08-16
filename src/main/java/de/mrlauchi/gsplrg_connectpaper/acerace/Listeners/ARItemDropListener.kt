package de.mrlauchi.gsplrg_connectpaper.acerace.Listeners

import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class ARItemDropListener : Listener {
    fun ondrop(event: PlayerDropItemEvent){
        if (AceRaceEssentials.getActive() == 1){
            event.isCancelled = true
        }
    }
}