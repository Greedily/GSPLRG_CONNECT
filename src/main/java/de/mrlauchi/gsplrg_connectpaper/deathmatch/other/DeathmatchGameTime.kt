package de.mrlauchi.gsplrg_connectpaper.deathmatch.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.ParticleEssentials
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable
import net.md_5.bungee.api.chat.TextComponent

object DeathmatchGameTime {
    fun startGameTime() {
        val config = Main.instance!!.config

        var minutetime = 10
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
                    if (secondtime < 10) {
                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§bKills: ${config.getInt("deathmatch.score.${target.name}")} || Remaining Time: $minutetime:0$secondtime"))
                    }else{
                        target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§bKills: ${config.getInt("deathmatch.score.${target.name}")} || Remaining Time: $minutetime:$secondtime"))
                    }


                    if(secondtime <= 0 && config.getInt("deathmatch.gamemodeactive") == 1 && minutetime > 0) {
                        minutetime -= 1
                        secondtime = 60
                    }
                    if (minutetime == 1) {
                        Bukkit.broadcastMessage("1 MINUTE LEFT!")
                    }
                    if (minutetime <= 0 && secondtime <= 0 && config.getInt("deathmatch.gamemodeactive") == 1) {
                        DeathmatchEssentials.setGamemodeEnabled(false)

                        var points = mutableMapOf<Int, String>()

                        target.gameMode = GameMode.SPECTATOR
                        target.inventory.clear()
                        target.sendTitle("GAME ENDS!", "")

                        Spawn.teleport(true)

                        for (countTarget in Bukkit.getOnlinePlayers()) {
                            //points.plus(config.getInt("deathmatch.points.${target.name}") to target.name)
                            points += Pair(config.getInt("deathmatch.score.${countTarget.name}") , countTarget.name)
                            countTarget.gameMode = GameMode.SPECTATOR
                            countTarget.inventory.clear()
                            ParticleEssentials.scoreparticle(countTarget)
                            DeathmatchEssentials.resetplayerstreak(countTarget)
                        }
                        points = points.toSortedMap(Comparator.reverseOrder())
                        val nameslist = points.values.toList()
                        val pointslist = points.keys.toList()
                        val firstplacement = nameslist[0]
                        val secondplacement = nameslist[1]
                        val thirdplacement = nameslist[2]
                        Bukkit.broadcastMessage(
                                "-------------------------------------------"+
                                "§61st.$firstplacement §fwith §6${pointslist[0]} kills." +
                                "§72nd.$secondplacement §fwith §7${pointslist[1]} kills." +
                                "§b3rd.$thirdplacement §fwith §b${pointslist[2]} kills." +
                                "4th.${nameslist[3]} with ${pointslist[3]} kills." +
                                "5th.${nameslist[4]} with ${pointslist[4]} kills." +
                                "6th.${nameslist[5]} with ${pointslist[5]} kills."+
                                "7th.${nameslist[6]} with ${pointslist[6]} kills." +
                                "8th.${nameslist[7]} with ${pointslist[7]} kills." +
                                "9th.${nameslist[8]} with ${pointslist[8]} kills." +
                                "10th.${nameslist[9]} with ${pointslist[9]} kills."+
                                "11th.${nameslist[10]} with ${pointslist[10]} kills." +
                                "12th.${nameslist[11]} with ${pointslist[11]} kills." +
                                "13th.${nameslist[12]} with ${pointslist[12]} kills." +
                                "13th.${nameslist[13]} with ${pointslist[13]} kills."+
                                "14th.${nameslist[14]} with ${pointslist[14]} kills." +
                                "15th.${nameslist[15]} with ${pointslist[15]} kills." +
                                "16th.${nameslist[16]} with ${pointslist[16]} kills." +
                                "17th.${nameslist[17]} with ${pointslist[17]} kills."+
                                "19th.${nameslist[18]} with ${pointslist[18]} kills." +
                                "20th.${nameslist[19]} with ${pointslist[19]} kills." +
                                "21th.${nameslist[20]} with ${pointslist[20]} kills." +
                                "22th.${nameslist[21]} with ${pointslist[21]} kills."+
                                "23th.${nameslist[22]} with ${pointslist[22]} kills." +
                                "24th.${nameslist[23]} with ${pointslist[23]} kills." +
                                "-------------------------------------------"
                        )
                        Spawn.teleport(true)
                        this.cancel()
                    }
                }
                secondtime -= 1

            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }
}