package de.mrlauchi.gsplrg_connectpaper.tgttos.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.pz.pz
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.th.th
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.ww1.ww1
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object Countdown {

    fun countdown(player: Player, gameName : String) {
        val config = Main.instance!!.config

        for (target in Bukkit.getOnlinePlayers()) {
            if(gameName == "ww1") {
                ww1.teleport(target)
            }
            if(gameName == "pz") {
                pz.teleport(target)
            }
            if(gameName == "th") {
                th.teleport(target)
            }
        }

        Essentials.setCountdownActive(1)

        var time = 10
        player.sendMessage("§bCountdown has been started!")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    target.gameMode = GameMode.ADVENTURE
                    target.inventory.clear()
                    target.health = 20.0
                    target.saturation = 20.0F
                    target.foodLevel = 20

                    target.sendTitle("§b$time", "")

                    target.isInvisible = true

                    target.isInvulnerable = true

                    if(time <= 0) {
                        target.sendTitle("§bStart!!!", "")

                        target.isInvisible = false

                        target.isInvulnerable = false

                        Essentials.setCountdownActive(0)

                        if(gameName == "ww1") {
                            ww1.start()
                        }
                        if(gameName == "pz") {
                            pz.start()
                        }
                        if(gameName == "th") {
                            th.start()
                        }

                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }

}