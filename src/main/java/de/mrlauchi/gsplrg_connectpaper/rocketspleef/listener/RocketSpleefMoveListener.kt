package de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener


import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Creeper
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
        if (RocketSpleefEssentials.getGameActive() == 1 && RocketSpleefEssentials.getCountdownActive() == 0) {
            if (event.to.y <= -5) {
                var aliveteams: MutableList<String> = ArrayList()
                player.gameMode = GameMode.SPECTATOR
                player.inventory.clear()
                for (player in Bukkit.getOnlinePlayers()) {
                    if (player.gameMode == GameMode.SPECTATOR) return
                    val targetteam = player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(player.name))!!.name
                    if (!aliveteams.contains(targetteam)) { // add all the alive teams into the list.
                        aliveteams += targetteam
                    }

                    if (aliveteams.size == 1) {
                        for (entity in Bukkit.getWorld("world")!!.entities) {
                            if(entity is Item) {
                                entity.remove()
                            }
                            if(entity is Creeper) {
                                entity.remove()
                            }
                        }

                        player.gameMode = GameMode.SPECTATOR
                        player.inventory.clear()

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
}