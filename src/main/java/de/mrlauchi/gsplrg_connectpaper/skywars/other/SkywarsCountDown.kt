package de.mrlauchi.gsplrg_connectpaper.skywars.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object SkywarsCountDown {

    fun countdown(player: Player) {
        val config = Main.instance!!.config

        for (target in Bukkit.getOnlinePlayers()) { // teleport players based on their team
            target.teleport(SkywarsEssentials.getCoords(target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name))
            target.inventory.clear()
        }


        var time = 10
        player.sendMessage("§bCountdown has been started!")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    if (config.getInt("skywars.gamemodeactive") == 0){
                        target.gameMode = GameMode.SPECTATOR
                        this.cancel()
                        return
                    }

                    /*if (config.getInt("skywars.countdownactive") == 0){
                        this.cancel()
                        return
                    }
                    */
                    target.gameMode = GameMode.ADVENTURE
                    target.health = 20.0
                    target.saturation = 20.0F
                    target.foodLevel = 20


                    target.sendTitle("", "§b$time")

                    target.isInvisible = true

                    target.isInvulnerable = true

                    if(time <= 0 || config.getInt("skywars.countdownactive") == 0) {
                        target.sendTitle("§bStart!!!", "")

                        target.isInvisible = false
                        target.isInvulnerable = false

                        target.gameMode = GameMode.SURVIVAL

                        config.set("skywars.countdownactive",0)
                        SkywarsEssentials.setGameModeEnabled(true) // enable death listeners

                        Bukkit.getWorld("world")!!.setGameRuleValue("keepInventory", "false") // this should work, wanna try? you alr? sry lol. aight
                        Bukkit.getWorld("world")!!.setGameRuleValue("fallDamage", "true") // lets test ima compile

                       // Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"gamerule keepInventory false")

                        Main.instance!!.saveConfig()

                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }


}