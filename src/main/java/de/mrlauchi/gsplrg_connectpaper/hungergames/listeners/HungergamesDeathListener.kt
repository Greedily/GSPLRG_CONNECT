package de.mrlauchi.gsplrg_connectpaper.hungergames.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.hungergames.other.HungergamesEssentials
import de.mrlauchi.gsplrg_connectpaper.other.ParticleEssentials
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Creeper
import org.bukkit.entity.Item
import org.bukkit.entity.Player
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
            val world = Bukkit.getWorld("world")

           // Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute in minecraft:overworld run fill 136 169 -26 335 112 174 air replace chest")//  -1032.403t
           // PasteSchem.paste(Location(world, 237.4, 133.8, 75.25),"hungergamesmap1")//-255.5, 25.8, -1032.4
          //  Bukkit.broadcastMessage("FILLED.")
            //set dead player's gamemode and clear him .
            deadplayer.gameMode = GameMode.SPECTATOR
            deadplayer.inventory.clear()

            pointsEssentials.addplayerpoints(event.player.killer!!, pointsModule.hungergames.killpoints)
            event.player.killer!!.sendMessage("+"+pointsModule.hungergames.killpoints+" for kill!")
            ParticleEssentials.scoreparticle(event.player.killer!!)
            ParticleEssentials.spawnfirework(event.player)


            config.set("hungergames.playerspawns.${deadplayer.name}.x",deadplayer.location.x)
            config.set("hungergames.playerspawns.${deadplayer.name}.y",deadplayer.location.y)
            config.set("hungergames.playerspawns.${deadplayer.name}.z",deadplayer.location.z)

            Main.instance!!.saveConfig()

            val aliveteams: MutableList<String> = ArrayList()
            val deadteams: MutableList<String> = ArrayList()
            val plrteam = deadplayer.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(deadplayer.name))!!.name
            for (target in Bukkit.getOnlinePlayers()) {
                val targetteam = target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
                if(target.gameMode == GameMode.ADVENTURE) {
                    if (!aliveteams.contains(targetteam)) { // add all the alive teams into the list.
                        aliveteams += targetteam
                    }
                }
                if(target.gameMode == GameMode.SPECTATOR) {
                    HungergamesEssentials.deadpeeps.plus(target.name)
                }
            }

            var numbersalive = 0
            for (target in HungergamesEssentials.deadpeeps){

                val targetplayer = Bukkit.getPlayer(target)
                val targetplrteam = targetplayer?.scoreboard?.getPlayerTeam(Bukkit.getOfflinePlayer(targetplayer.name))!!.name

                if (targetplayer.gameMode != GameMode.ADVENTURE && targetplrteam != plrteam) return
                numbersalive += 1
            }
            if (numbersalive <= 0){ // team dead
                HungergamesEssentials.setteamPlacement(plrteam)
                Bukkit.broadcastMessage("${plrteam} died!")
            }

            if (aliveteams.size <= 1 || HungergamesEssentials.currentteamplacement == 1 && config.getInt("hungergames.gamemodeactive") == 1) {
                // check if any more team member alive
                for (entity in Bukkit.getWorld("world")!!.entities) {
                    if(entity is Item) {
                        entity.remove()
                    }
                    if(entity is Creeper) {
                        entity.remove()
                    }
                }

                event.drops.clear()

                HungergamesEssentials.setCountDownEnabled(false)

                val x = config.getDouble("hungergames.mapspawn.x")
                val y = config.getDouble("hungergames.mapspawn.y")
                val z = config.getDouble("hungergames.mapspawn.z")


                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"execute in minecraft:overworld run fill -155 58 -1134 -356 4 -933 minecraft:air hollow")//  -1032.403t
                PasteSchem.paste(Location(world, x, y, z),"hungergamesmap1")//-108.520, 49.0, -1780.435
                //Bukkit.broadcastMessage("FILLED.")
                Bukkit.broadcastMessage("Game Ended.")
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"worldborder set 1000000")

                for (target in Bukkit.getOnlinePlayers()) { // send titles to winning players and rest
                    target.inventory.clear()
                    if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name != aliveteams[0]) { // all people
                        target.sendTitle("§6${aliveteams[0]} Won The Game!", "")
                    }  // the winner
                    if (target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name == aliveteams[0]) {
                        target.sendTitle("§6Your Team Won!", "")
                        target.gameMode = GameMode.SPECTATOR
                        pointsEssentials.addplayerpoints(target, pointsModule.hungergames.winpoints)
                        target.sendMessage("+${pointsModule.hungergames.winpoints} Points for wining!")
                        HungergamesEssentials.setteamPlacement(target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name)
                    }
                }

                for (msg in HungergamesEssentials.endmsg.reversed()) {
                    Bukkit.broadcastMessage(msg.toString())
                }
                HungergamesEssentials.resetplacements()
                HungergamesEssentials.setGameModeEnabled(false)
                pointsEssentials.updateteampoints()

                Spawn.teleport(true)

            }
        }
    }
}