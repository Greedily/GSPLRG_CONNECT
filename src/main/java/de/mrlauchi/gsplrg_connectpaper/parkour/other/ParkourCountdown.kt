package de.mrlauchi.gsplrg_connectpaper.parkour.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourStartCommand
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object ParkourCountdown {

    fun countdown(player: Player) {
        val config = Main.instance!!.config

        for (target in Bukkit.getOnlinePlayers()) {
            target.teleport(ParkourEssentials.getCoordinate(0))
            config.set("parkour.playersfinished.${target.name}", 0)
        }

        ParkourEssentials.setActive(1)

        ParkourEssentials.setCountdownActive(1)

        var time = 10
        player.sendMessage("§bCountdown has been started!")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    target.gameMode = GameMode.ADVENTURE
                    target.inventory.clear()
                    target.health = 20.0
                    target.saturation = 20.0F
                    target.foodLevel = 20
                    target.inventory.boots = ItemStack(Material.LEATHER_BOOTS)

                    target.sendTitle("§b$time", "")

                    target.isInvisible = true

                    target.isInvulnerable = true

                    if(time <= 0) {
                        target.sendTitle("§bStart!!!", "")

                        //target.isInvisible = false

                        //target.isInvulnerable = false

                        ParkourEssentials.setCountdownActive(0)

                        ParkourStartCommand.start(player)

                        ParkourEssentials.startTimer()
                        ParkourTimer.start()

                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }

}