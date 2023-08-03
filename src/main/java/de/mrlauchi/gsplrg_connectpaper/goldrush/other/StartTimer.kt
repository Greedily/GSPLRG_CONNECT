package de.mrlauchi.gsplrg_connectpaper.goldrush.other

import de.mrlauchi.gsplrg_connectpaper.Main
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

object StartTimer {

    fun starttimer() {
        var currenttime: Int = 600
        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                // minute:second  // 1min:30sec
                val config = Main.instance!!.config

                var totalminutes: Int = currenttime / 60
                var totalSecondsLeft = currenttime-(totalminutes*60)

                for (player in Bukkit.getOnlinePlayers()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent("§b$totalminutes:$totalSecondsLeft"))
                }

                if(currenttime <= 0) {
                    this.cancel()
                }

                if(config.getInt("goldrush.stoptimer") == 1) {
                    currenttime = 0
                    this.cancel()

                   // config.set("goldrush.stoptimer", 0)
                    //Main.instance!!.saveConfig()
                }
                currenttime -= 1
            }
        }.runTaskTimer(Main.instance!!,0,20)
    }

}