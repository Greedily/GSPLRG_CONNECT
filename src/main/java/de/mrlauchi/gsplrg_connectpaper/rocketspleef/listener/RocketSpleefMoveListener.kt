package de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener


import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
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
            if(event.from.x != event.to.x || event.from.z != event.to.z) {
                event.isCancelled = true
            }
        }
        if (RocketSpleefEssentials.getActive() == 1 && RocketSpleefEssentials.getCountdownActive() == 0) {
            if (event.to.y <= -30) {
                if (player.gameMode == GameMode.SPECTATOR) return
                val time = Main.instance!!.config.getString("rocketspleef.playertimes.${player.name}")
                Bukkit.broadcastMessage("${player.name} Died! and was alive for §c$time")
                player.gameMode = GameMode.SPECTATOR
                player.inventory.clear()
                for (target in Bukkit.getOnlinePlayers()) {
                    val aliveteams: MutableList<String> = ArrayList()
                    val targetteam = player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
                    if(target.gameMode != GameMode.SPECTATOR) {
                        if (!aliveteams.contains(targetteam)) { // add all the alive teams into the list.
                            aliveteams += targetteam
                        }
                    }



                    if (aliveteams.size == 1) {
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

                        RocketSpleefEssentials.setActive(false)

                        for (target in Bukkit.getOnlinePlayers()) { // send titles to winning players and rest
                            target.inventory.clear()
                            if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name != aliveteams[0]) { // all people
                                target.sendTitle("§6${aliveteams[0]} Won The Game!", "")
                            }  // the winner
                            if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name == aliveteams[0]) {
                                target.sendTitle("§6Your Team Won!", "")//e
                            }
                        }
                    }

                }
            }
        }
    }
}