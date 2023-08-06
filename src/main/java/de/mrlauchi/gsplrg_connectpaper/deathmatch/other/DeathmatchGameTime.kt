package de.mrlauchi.gsplrg_connectpaper.deathmatch.other

import de.mrlauchi.gsplrg_connectpaper.Main
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable
import net.md_5.bungee.api.chat.TextComponent

object DeathmatchGameTime {
    fun startGameTime() {
        val config = Main.instance!!.config

        var minutetime = 5
        var secondtime = 0

        var points = mutableMapOf<Int, String>()

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    target.saturation = 20.0F
                    target.foodLevel = 20
                    if (config.getInt("deathmatch.gamemodeactive") == 0) {
                        this.cancel()
                    }
                    target.gameMode = GameMode.ADVENTURE
                    target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§bKills: ${config.getInt("deathmatch.score.${target.name}")} || Time: $minutetime:$secondtime"))

                    if(secondtime <= 0 && config.getInt("deathmatch.gamemodeactive") == 1 && minutetime > 0) {
                        minutetime -= 1
                        secondtime = 60
                    }
                    if (minutetime <= 0 && secondtime <= 0 && config.getInt("deathmatch.gamemodeactive") == 1) {
                        DeathmatchEssentials.setGamemodeEnabled(false)

                        target.gameMode = GameMode.SPECTATOR
                        target.inventory.clear()
                        target.sendTitle("GAME ENDS!", "")

                        for (target in Bukkit.getOnlinePlayers()) {
                            //points.plus(config.getInt("deathmatch.points.${target.name}") to target.name)
                            points += Pair(config.getInt("deathmatch.points.${target.name}") , target.name)
                            target.gameMode = GameMode.SPECTATOR
                            target.inventory.clear()
                        }
                        Bukkit.broadcastMessage(points.toString())
                        points = points.toSortedMap(Comparator.reverseOrder())
                        val firstplacement = points[0]
                        val secondplacement = points[1]
                        val thirdplacement = points[2]
                        Bukkit.broadcastMessage(points.toString())
                        Bukkit.broadcastMessage("1st.$firstplacement  2nd.$secondplacement  3rd.$thirdplacement")
                        this.cancel()
                    }
                }
                secondtime -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }
}