package de.mrlauchi.gsplrg_connectpaper.parkour.listener

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import org.bukkit.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import de.mrlauchi.gsplrg_connectpaper.other.*
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule

class ParkourMoveListener : Listener {

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player
        if(ParkourEssentials.getCountdownActive() == 1) {
            Countdown.freeze(event)
        }
        if(ParkourEssentials.getActive() != 1) return
        if(player.gameMode != GameMode.ADVENTURE) return

        PlayerUtil.heal(player)
        ParkourEssentials.yKill(player)

        val radius = 5

        for (i in 0..20) {
            if (player.location.distance(ParkourEssentials.getCoordinate(i)) >= radius) return
            if(i != ParkourEssentials.getSection(player)) {
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)
                if(i + 1 != 21) {
                    player.sendMessage("§bYou've reached stage ${i + 1} + ${pointsModule.parkour.perlevelpoint} point!")
                    pointsEssentials.addplayerpoints(player, pointsModule.parkour.perlevelpoint)
                }

                if (i != 20){ // to spawn our own particle
                    ParticleEssentials.spawnfirework(player)
                }
            }

            ParkourEssentials.setSection(player, i)

            ParkourEssentials.finishLogic(i, player)
        }
    }

}