package de.mrlauchi.gsplrg_connectpaper.tgttos.other

import de.mrlauchi.gsplrg_connectpaper.Main
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import  net.md_5.bungee.api.chat.TextComponent

object TGTTOSTimer {

    fun start() {
        var currentsecondtime: Int = 0
        var currentminutetime: Int = 0

        Bukkit.broadcastMessage("timer started. yey")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                //idea.:


                val config = Main.instance!!.config

                for (player in Bukkit.getOnlinePlayers()) {
                    if (config.getInt("tgttos.playersfinished.${player.name}") == 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§b$currentminutetime:$currentsecondtime"))
                        //save player time
                        val playertime = "$currentminutetime:$currentsecondtime"
                        config.set("tgttos.playertimes.${player.name}", playertime)
                        Main.instance!!.saveConfig()

                    }

                }


                if(currentsecondtime >= 60) {
                    currentminutetime += 1
                    currentsecondtime = 0
                }

                if(config.getInt("tgttos.timeractive") == 0 || Essentials.getActive() == null) {
                    //save player time.
                    Bukkit.broadcastMessage("stopped timer.")
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