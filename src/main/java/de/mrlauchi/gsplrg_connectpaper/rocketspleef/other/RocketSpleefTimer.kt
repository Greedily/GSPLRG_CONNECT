package de.mrlauchi.gsplrg_connectpaper.parkour.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable

object RocketSpleefTimer {
    fun start() {
        var currentsecondtime: Int = 0
        var currentminutetime: Int = 0

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                //idea.:

                val config = Main.instance!!.config

                for (player in Bukkit.getOnlinePlayers()) {//e
                    if (player.gameMode == GameMode.ADVENTURE) {
                        //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent("§b$currentminutetime:$currentsecondtime"))
                        //save player time
                        val playertime = "$currentminutetime:$currentsecondtime"
                        config.set("rocketspleef.playertimes.${player.name}", playertime) // we stop it in the pkmovelistener
                        Main.instance!!.saveConfig()
                    }

                }

                if(currentsecondtime >= 60) {
                    currentminutetime += 1
                    currentsecondtime = 0
                }

                if(config.getInt("rocketspleef.timeractive") == 0 || RocketSpleefEssentials.getActive() == 0) {
                    //save player time.

                    currentsecondtime = 0
                    currentminutetime = 0
                    this.cancel()

                }
                // wait a sec.
                currentsecondtime += 1
            }
        }.runTaskTimer(Main.instance!!,0,20)
    }

}