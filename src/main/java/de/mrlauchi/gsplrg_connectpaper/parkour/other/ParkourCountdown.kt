package de.mrlauchi.gsplrg_connectpaper.parkour.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.PlayerUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object ParkourCountdown {

    fun countdown(player: Player) {
        val config = Main.instance!!.config

        for (target in Bukkit.getOnlinePlayers()) {
            target.teleport(ParkourEssentials.getCoordinate(0))

            PlayerUtil.clear(target)
            PlayerUtil.heal(target)
            PlayerUtil.setVisVul(target)
            PlayerUtil.adventure(target)

            config.set("parkour.playersfinished.${target.name}", 0)
        }

        ParkourEssentials.setActive(1)

        ParkourEssentials.setCountdownActive(1)

        var time = 10
        player.sendMessage("§bCountdown has been started!")

        object: BukkitRunnable(){
            override fun run() {
                for (targett in Bukkit.getOnlinePlayers()) {

                    targett.inventory.boots = ItemStack(Material.LEATHER_BOOTS)

                    targett.sendTitle("§b$time", "")

                    if(time <= 0) {
                        targett.sendTitle("§bStart!!!", "")

                        ParkourEssentials.setCountdownActive(0)
                        ParkourTimer.start()

                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }

}