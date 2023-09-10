package de.mrlauchi.gsplrg_connectpaper.parkour.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourStopCommand
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

object ParkourTimer {
    fun start() {
        ParkourEssentials.startTimer()
        var currentSecondTime = 0
        var currentMinuteTime = 0

        object: BukkitRunnable(){
            override fun run() {
                val config = Main.instance!!.config

                for (player in Bukkit.getOnlinePlayers()) {
                    if (config.getInt("parkour.playersfinished.${player.name}") == 0) {
                        ParkourEssentials.timerTitleLogic(currentSecondTime, currentMinuteTime, player)
                        ParkourEssentials.savePlayerTime(currentSecondTime, currentMinuteTime, player)
                    }

                }

                if(currentSecondTime >= 60) {
                    currentMinuteTime += 1
                    currentSecondTime = 0
                }



                if(config.getInt("parkour.timeractive") == 0 || ParkourEssentials.getActive() == 0 || currentMinuteTime >= 15) {
                    //save player time.

                    currentSecondTime = 0
                    currentMinuteTime = 0
                    Spawn.teleport(true)

                    ParkourEssentials.stopTimer()
                    for (player in Bukkit.getOnlinePlayers()){
                        ParkourStopCommand.stop(player)
                    }
                    this.cancel()

                }
                // wait a sec.
                currentSecondTime += 1
            }
        }.runTaskTimer(Main.instance!!,0,20)
    }

}