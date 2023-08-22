package de.mrlauchi.gsplrg_connectpaper.hungergames.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.ParticleEssentials
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import de.mrlauchi.gsplrg_connectpaper.skywars.other.SkywarsEssentials
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Creeper
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class SkywarsDeathListener:Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val config = Main.instance!!.config

        val deadplayer = event.entity
        //var aliveteams = listOf<String>()
        // teamname to no.alive
        if (config.getInt("skywars.gamemodeactive") == 1) { // if game is active.
            var aliveteams: MutableList<String> = ArrayList()

            val world = Bukkit.getWorld("world")
            if (event.player.killer is Player){
                pointsEssentials.addplayerpoints(event.player.killer!!, pointsModule.skywars.killpoints)
                ParticleEssentials.spawnfirework(event.player)

                event.player.killer!!.sendMessage("+"+pointsModule.skywars.killpoints+" for kill!")
                ParticleEssentials.scoreparticle(event.player.killer!!)
            }
          //Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute in minecraft:overworld run fill -106 235 114 33 181 -32 minecraft:air hollow")
          //PasteSchem.paste(Location(world, -36.898, 200.0, 41.8), "skywarsmap1")
            //set dead player's gamemode and clear him .
            deadplayer.gameMode = GameMode.SPECTATOR
            deadplayer.inventory.clear()


            for (target in Bukkit.getOnlinePlayers()) {
                if (target.gameMode == GameMode.SURVIVAL) { // only alive players
                    val targetteam = target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
                    if (!aliveteams.contains(targetteam)) { // add all the alive teams into the list.
                        aliveteams += targetteam
                    }
                    pointsEssentials.addplayerpoints(target, pointsModule.skywars.outlivedpoints) // outlived points
                    target.sendMessage("+${pointsModule.skywars.outlivedpoints} points")
                }
            }

            for (target in Bukkit.getOnlinePlayers()) { // for every single player. add their teams. if one team remaining GO THROUGH EVERY PLAYER AGAIN?

                // GAME END. <=1 so it can work in my server
                if (aliveteams.size == 1 && config.getInt("skywars.gamemodeactive") == 1) {
                    // check if any more team member alive
                    for (entity in Bukkit.getWorld("world")!!.entities) {
                        if(entity is Item) {
                            entity.remove()
                        }
                        if(entity is Creeper) {
                            entity.remove()
                        }
                    }

                    target.gameMode = GameMode.SPECTATOR
                    target.inventory.clear()
                    
                    event.drops.clear()

                    SkywarsEssentials.setGameModeEnabled(false)
                    SkywarsEssentials.setCountDownEnabled(false)

                    val x = config.getDouble("skywars.mapspawn.x")
                    val y = config.getDouble("skywars.mapspawn.y")
                    val z = config.getDouble("skywars.mapspawn.z")

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute in minecraft:overworld run fill -178 36 -1709 -32 84 -1853 minecraft:air hollow")//  fill -178 36 -1709 -32 84 -1853 minecraft:air hollow
                    PasteSchem.paste(Location(world, x, y, z),"skywarsmap1")//-108.520, 49.0, -1780.435



                    for (target in Bukkit.getOnlinePlayers()) { // send titles to winning players and rest
                        target.inventory.clear()
                        if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name != aliveteams[0]) { // all people
                            target.sendTitle("§6${aliveteams[0]} Won The Game!", "")
                        }  // the winner
                        if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name == aliveteams[0]) {
                            target.sendTitle("§6Your Team Won!", "")
                            target.gameMode = GameMode.SPECTATOR
                            pointsEssentials.addplayerpoints(target, pointsModule.skywars.winpoints)
                            ParticleEssentials.scoreparticle(target)
                        }
                    }
                    Spawn.teleport(true)

                }

            }
        }
    }
}