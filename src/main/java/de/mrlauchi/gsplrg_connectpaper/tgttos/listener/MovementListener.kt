package de.mrlauchi.gsplrg_connectpaper.tgttos.listener

import de.mrlauchi.gsplrg_connectpaper.tgttos.other.Essentials
import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector

class MovementListener : Listener {

    @EventHandler
    fun onMove(event : PlayerMoveEvent) {
        val player = event.player
        if(Essentials.getActive() != null) {
            val name = Essentials.getActive()
            if(player.gameMode == GameMode.ADVENTURE) {
                if (Essentials.getActive() == Main.instance!!.WW1()) {
                    if (player.location.distance(Essentials.getEndLoc(name!!)) <= 5) {
                        player.gameMode = GameMode.SPECTATOR
                        Bukkit.broadcastMessage("§b${player.name} finished!")
                    }
                }
                if (Essentials.getActive() == Main.instance!!.PZ()) {
                    if (player.location.distance(Essentials.getEndLoc(name!!)) <= 4) {
                        player.gameMode = GameMode.SPECTATOR
                        Bukkit.broadcastMessage("§b${player.name} finished!")
                    }

                    val locBelow = player.getLocation()
                    locBelow.y = locBelow.y - 1

                    if(locBelow.block.type == Material.RED_CONCRETE) {
                        if (event.to?.y!! > event.from.y) {
                            player.velocity = Vector(player.location.direction.x, 0.75, player.location.direction.z)
                        }
                    }

                    locBelow.y -= 1

                    if(locBelow.block.type == Material.SOUL_SOIL) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20*4, 2, false))
                    }

                    if(player.location.y <= -20.0) {
                        player.health = 0.0
                    }
                }
                if (Essentials.getActive() == Main.instance!!.TH()) {
                    for (i in 0..64) {
                        if (player.location.distance(Essentials.getEndLoc(name!!).add(0.0, 0.0, i.toDouble())) <= 7) {
                            player.gameMode = GameMode.SPECTATOR
                            Bukkit.broadcastMessage("§b${player.name} finished!")
                            event.isCancelled = true
                            return
                        }
                    }
                }
            }
        }

        if(Essentials.getCountdownActive() == 1) {
            if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
                event.isCancelled = true
            }
        }
    }

}