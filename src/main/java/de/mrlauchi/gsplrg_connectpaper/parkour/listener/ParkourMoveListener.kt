package de.mrlauchi.gsplrg_connectpaper.parkour.listener

import de.mrlauchi.gsplrg_connectpaper.parkour.other.ParkourEssentials
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.ParticleEssentials
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourStopCommand
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule

class ParkourMoveListener : Listener {

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player
        val config = Main.instance!!.config
        if(ParkourEssentials.getCountdownActive() == 1) {
            if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
                event.isCancelled = true
            }
        }
        if(ParkourEssentials.getActive() == 1) {
            if(player.gameMode == GameMode.ADVENTURE) {
                if(player.location.y <= -5.0) {
                    player.health = 0.0
                }
                player.saturation = 20.0F
                player.foodLevel = 20

                val radius = 5

                for (i in 0..20) {
                    if (player.location.distance(ParkourEssentials.getCoordinate(i)) <= radius) {
                        if(i != ParkourEssentials.getSection(player)) {
                            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)

                            if(i + 1 != 21) {
                                player.sendMessage("§bYou've reached stage ${i + 1} + ${pointsModule.parkour.perlevelpoint} point!")
                                pointsEssentials.addplayerpoints(player, pointsModule.parkour.perlevelpoint)
                            }

                            val location : Location = player.getLineOfSight(null, 2).last().location
                            if (i != 20){ // to spawn our own particle
                                ParticleEssentials.spawnfirework(player)
                            }

                        }


                        ParkourEssentials.setSection(player, i)
                        //Bukkit.broadcastMessage(ParkourEssentials.getSection(player).toString())

                        if(i == 20) {
                            player.gameMode = GameMode.SPECTATOR
                            ParticleEssentials.scoreparticle(player)
                            val playertime = config.getString("parkour.playertimes.${player.name}")
                            Bukkit.broadcastMessage("§b${player.name} finished in $playertime!")
                            config.set("parkour.playersfinished.${player.name}", 1)
                            ParkourEssentials.setPlacement(player)
                            Main.instance!!.saveConfig()
                            var RemainingPlayers = listOf<String?>()
                            for (player in Bukkit.getOnlinePlayers()){
                                if (player.gameMode == GameMode.ADVENTURE){
                                    RemainingPlayers += player.name
                                }
                            }
                            Bukkit.broadcastMessage("Remaining Players: ${RemainingPlayers.size}")
                            if (RemainingPlayers.size <= 0){
                                for (msg in ParkourEssentials.endmsg){
                                    Bukkit.broadcastMessage(msg.toString())
                                }

                                ParkourStopCommand.stop(player)
                                Spawn.teleport(true)
                                ParkourEssentials.setActive(0)
                                ParkourEssentials.resetplacements()
                            }
                            //finish timer
                            //ParkourEssentials.stopTimer()

                        }

                    }
                }
            }
        }
    }

}