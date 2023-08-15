package de.mrlauchi.gsplrg_connectpaper.tgttos.listener

import de.mrlauchi.gsplrg_connectpaper.tgttos.other.Essentials
import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
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

                        playerendFun(player)
                    }
                }
                if (Essentials.getActive() == Main.instance!!.PZ()) {
                    if (player.location.distance(Essentials.getEndLoc(name!!)) <= 4) {
                        player.gameMode = GameMode.SPECTATOR
                        Bukkit.broadcastMessage("§b${player.name} finished!")

                        playerendFun(player)
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
                            playerendFun(player)
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

    fun playerendFun(player: Player) {
        val config = Main.instance!!.config
        val playerTime = config.getString("tgttos.playertimes.${player.name}")
        Bukkit.broadcastMessage("§b${player.name} finished in $playerTime!")
        config.set("tgttos.playersfinished.${player.name}", 1)
        Essentials.setPlacement(player)

        Main.instance!!.saveConfig()
        val RemainingPlayers = mutableListOf<String?>()
        for (target2 in Bukkit.getOnlinePlayers()){
            if (target2.gameMode == GameMode.ADVENTURE){
                RemainingPlayers += target2.name

            }
        }
        pointsEssentials.addplayerpoints(player, pointsModule.tgttos.placementlist[Essentials.currentplacement]!!)
        Bukkit.broadcastMessage("Remaining Players: ${RemainingPlayers.size}")
        if (RemainingPlayers.isEmpty()){
            for (target in Bukkit.getOnlinePlayers()) {
                config.set("tgttos.playersfinished.${target.name}", 0)
                Main.instance!!.saveConfig()
            }
            for (msg in Essentials.endmsg){
                Bukkit.broadcastMessage(msg.toString())
            }

            Spawn.teleport(true)
            Essentials.stopTimer()
            Essentials.setActive(null)
            Essentials.resetplacements()
        }
    }

}