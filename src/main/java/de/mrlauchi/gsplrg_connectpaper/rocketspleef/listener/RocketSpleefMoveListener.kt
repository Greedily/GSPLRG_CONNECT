package de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener


import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.ParticleEssentials
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Creeper
import org.bukkit.entity.Fireball
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class RocketSpleefMoveListener : Listener {
    @EventHandler
    fun onMove(event: PlayerMoveEvent){
        val player = event.player
        if(RocketSpleefEssentials.getCountdownActive() == 1) {
            if(event.from.x != event.to.x || event.from.z != event.to.z) {//e
                event.isCancelled = true
            }
        }
        if (RocketSpleefEssentials.getActive() == 1 && RocketSpleefEssentials.getCountdownActive() == 0) {
            if (event.to.y <= -30) {
                if (player.gameMode == GameMode.SPECTATOR) return
                val time = Main.instance!!.config.getString("rocketspleef.playertimes.${player.name}")
                Bukkit.broadcastMessage("${player.name} Died! and was alive for §c$time")
                //RocketSpleefEssentials.setPlacement(player)

                pointsEssentials.addplayerpoints(player, pointsModule.rocketspleef.placementlist[RocketSpleefEssentials.currentteamplacement]!!)
                player.gameMode = GameMode.SPECTATOR
                player.inventory.clear()
                val aliveteams: MutableList<String> = ArrayList()
                val deadteams: MutableList<String> = ArrayList()
                val plrteam = player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(player.name))!!.name
                for (target in Bukkit.getOnlinePlayers()) {
                    val targetteam = target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
                    if(target.gameMode == GameMode.ADVENTURE) {
                        if (!aliveteams.contains(targetteam)) { // add all the alive teams into the list.
                            aliveteams += targetteam
                        }
                    }
                    if(target.gameMode == GameMode.SPECTATOR) {
                        RocketSpleefEssentials.deadpeeps.plus(target.name)
                    }
                }
                var numbersalive = 0
                for (target in RocketSpleefEssentials.deadpeeps){

                    val targetplayer = Bukkit.getPlayer(target)
                    val targetplrteam = targetplayer?.scoreboard?.getPlayerTeam(Bukkit.getOfflinePlayer(targetplayer.name))!!.name

                    if (targetplayer.gameMode != GameMode.ADVENTURE && targetplrteam != plrteam) return
                    numbersalive += 1
                }
                if (numbersalive <= 0){ // team dead
                    RocketSpleefEssentials.setteamPlacement(plrteam)
                    Bukkit.broadcastMessage("${plrteam} died!")
                }

                if (aliveteams.size <= 1 || RocketSpleefEssentials.currentteamplacement == 1) {
                    RocketSpleefEssentials.setteamPlacement(aliveteams[0]) // get last team (8th most prob)
                    for (entity in Bukkit.getWorld("world")!!.entities) {
                        if(entity is Item) {
                            entity.remove()
                        }
                        if(entity is Creeper) {
                            entity.remove()
                        }
                        if(entity is Fireball) {
                            entity.remove()
                        }
                    }


                    val map = Main.instance!!.config.getString("rocketspleef.currentmap")
                    PasteSchem.paste(Location(Bukkit.getWorld("world"),-385.508, 109.0, -2055.485), map)

                    player.gameMode = GameMode.SPECTATOR
                    player.inventory.clear()
                    player.isInvisible = false

                    for (target2 in Bukkit.getOnlinePlayers()) { // send titles to winning players and rest
                        target2.inventory.clear()
                        if (target2.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target2.name))!!.name != aliveteams[0]) { // all people
                            target2.sendTitle("§6${aliveteams[0]} Won The Game!", "")
                        }  // the winner
                        if (target2.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target2.name))!!.name == aliveteams[0]) {
                            target2.sendTitle("§6Your Team Won!", "")//e
                            ParticleEssentials.scoreparticle(target2)
                        }
                    }

                    for (msg in RocketSpleefEssentials.endmsg.reversed()){
                        Bukkit.broadcastMessage(msg.toString())
                    }

                    RocketSpleefEssentials.resetplacements()
                    pointsEssentials.updateteampoints()
                    Spawn.teleport(true)
                    RocketSpleefEssentials.setActive(false)
                }
            }
        }
    }
}