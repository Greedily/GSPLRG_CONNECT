package de.mrlauchi.gsplrg_connectpaper.hungergames.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.hungergames.other.HungergamesEssentials
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Creeper
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class HungergamesDeathListener:Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val config = Main.instance!!.config

        val deadplayer = event.entity
        //var aliveteams = listOf<String>()
        // teamname to no.alive
        if (config.getInt("hungergames.gamemodeactive") == 1) { // if game is active.
            var aliveteams: MutableList<String> = ArrayList()

            val world = Bukkit.getWorld("world")

           // Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute in minecraft:overworld run fill 136 169 -26 335 112 174 air replace chest")//  -1032.403t
           // PasteSchem.paste(Location(world, 237.4, 133.8, 75.25),"hungergamesmap1")//-255.5, 25.8, -1032.4
          //  Bukkit.broadcastMessage("FILLED.")
            //set dead player's gamemode and clear him .
            deadplayer.gameMode = GameMode.SPECTATOR
            deadplayer.inventory.clear()

            config.set("hungergames.playerspawns.${deadplayer.name}.x",deadplayer.location.x)
            config.set("hungergames.playerspawns.${deadplayer.name}.y",deadplayer.location.y)
            config.set("hungergames.playerspawns.${deadplayer.name}.z",deadplayer.location.z)

            Main.instance!!.saveConfig()

            for (target in Bukkit.getOnlinePlayers()) {
                if (target.gameMode == GameMode.ADVENTURE) { // only alive players
                    val targetteam = target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
                    if (!aliveteams.contains(targetteam)) { // add all the alive teams into the list.
                        aliveteams += targetteam
                    }
                }
            }

            for (target in Bukkit.getOnlinePlayers()) { // for every single player. add their teams. if one team remaining GO THROUGH EVERY PLAYER AGAIN?

                // GAME END. <=1 so it can work in my server
                if (aliveteams.size == 1 && config.getInt("hungergames.gamemodeactive") == 1) {
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

                    HungergamesEssentials.setGameModeEnabled(false)
                    HungergamesEssentials.setCountDownEnabled(false)

                    val x = config.getDouble("hungergames.mapspawn.x")
                    val y = config.getDouble("hungergames.mapspawn.y")
                    val z = config.getDouble("hungergames.mapspawn.z")

                    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute in minecraft:overworld run fill -155 58 -1134 -356 4 -933 minecraft:air hollow")//  -1032.403t
                    PasteSchem.paste(Location(world, x, y, z),"hungergamesmap1")//-108.520, 49.0, -1780.435
                    //Bukkit.broadcastMessage("FILLED.")
                    Bukkit.broadcastMessage("Game Ended.")


                    for (target in Bukkit.getOnlinePlayers()) { // send titles to winning players and rest
                        target.inventory.clear()
                        if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name != aliveteams[0]) { // all people
                            target.sendTitle("§6${aliveteams[0]} Won The Game!", "")
                        }  // the winner
                        if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name == aliveteams[0]) {
                            target.sendTitle("§6Your Team Won!", "")
                            target.gameMode = GameMode.SPECTATOR
                        }
                    }

                }

            }
        }
    }
}