package de.mrlauchi.gsplrg_connectpaper.acerace.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

object AceRaceTimer {
    fun start() {
        var currentsecondtime: Int = 0
        var currentminutetime: Int = 0
        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                //idea.:
                val config = Main.instance!!.config
                for (player in Bukkit.getOnlinePlayers()) {
                    if (config.getInt("acerace.playersfinished.${player.name}") == 0) {
                        if (currentsecondtime < 10) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent("§b$currentminutetime:0$currentsecondtime"))
                        }else{
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent("§b$currentminutetime:$currentsecondtime"))
                        }


                        //save player time
                        val playertime = "$currentminutetime:$currentsecondtime"
                        config.set("acerace.playertimes.${player.name}", playertime) // we stop it in the pkmovelistener
                        Main.instance!!.saveConfig()
                    }

                }

                if(currentsecondtime >= 59) {
                    currentminutetime += 1
                    currentsecondtime = -1
                }

                if(config.getInt("acerace.timeractive") == 0 || AceRaceEssentials.getActive() == 0) {
                    //save player time.

                    currentsecondtime = 0
                    currentminutetime = 0
                    pointsEssentials.updateteampoints()
                    this.cancel()

                }
                // wait a sec.
                currentsecondtime += 1
            }
        }.runTaskTimer(Main.instance!!,0,20)
    }

}