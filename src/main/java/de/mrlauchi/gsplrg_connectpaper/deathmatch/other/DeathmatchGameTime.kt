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

        var minutetime = 1
        var secondtime = 0


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

                        var points = mutableMapOf<Int, String>()

                        target.gameMode = GameMode.SPECTATOR
                        target.inventory.clear()
                        target.sendTitle("GAME ENDS!", "")

                        for (countTarget in Bukkit.getOnlinePlayers()) {
                            //points.plus(config.getInt("deathmatch.points.${target.name}") to target.name)
                            points += Pair(config.getInt("deathmatch.score.${countTarget.name}") , countTarget.name)
                            countTarget.gameMode = GameMode.SPECTATOR
                            countTarget.inventory.clear()
                        }
                        points = points.toSortedMap(Comparator.reverseOrder())
                        val nameslist = points.values.toList()
                        val pointslist = points.keys.toList()
                        val firstplacement = nameslist[0]
                        val secondplacement = nameslist[1]
                        val thirdplacement = nameslist[2]
                        Bukkit.broadcastMessage("1st.$firstplacement with ${pointslist[0]} 2nd.$secondplacement with ${pointslist[1]} 3rd.$thirdplacement with ${pointslist[2]}")
                        this.cancel()
                    }
                }
                secondtime -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }
}