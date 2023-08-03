package de.mrlauchi.gsplrg_connectpaper.acerace.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object AceRaceCountDown {

    fun countdown(player: Player) {
        val config = Main.instance!!.config

        for (target in Bukkit.getOnlinePlayers()) { // teleport players based on their team
            target.teleport(AceRaceEssentials.getStartCoords(target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name))
            target.inventory.clear()
            //give trident.
            val trident = ItemStack(Material.TRIDENT)
            val meta = trident.itemMeta
            meta?.addEnchant(Enchantment.RIPTIDE,3,true)
            meta?.isUnbreakable = true
            meta?.setDisplayName("Missile.")
            trident.setItemMeta(meta)
            target.inventory.addItem(trident)

            config.set("acerace.playermaprotations.${target.name}", 0)
            config.set("acerace.score.${target.name}", 0)
        }


        var time = 10
        player.sendMessage("§bCountdown has been started!")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    if (config.getInt("acerace.gamemodeactive") == 0){
                        target.gameMode = GameMode.SPECTATOR
                        this.cancel()
                        return
                    }

                    target.gameMode = GameMode.ADVENTURE
                    target.health = 20.0
                    target.saturation = 20.0F
                    target.foodLevel = 20


                    target.sendTitle("", "§b$time")

                    target.isInvisible = true

                    target.isInvulnerable = true

                    if(time <= 0 || config.getInt("acerace.countdownactive") == 0) {
                        target.sendTitle("§bStart!!!", "")

                        target.isInvisible = true
                        target.isInvulnerable = true


                        config.set("acerace.countdownactive",0)
                        AceRaceEssentials.setGameModeEnabled(true)

                        AceRaceEssentials.startTimer()
                        AceRaceTimer.start()

                        Main.instance!!.saveConfig()

                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }


}