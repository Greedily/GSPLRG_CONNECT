package de.mrlauchi.gsplrg_connectpaper.rocketspleef.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourStartCommand
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object RocketSpleefCountdown {

    fun countdown(player: Player, map : String) {
        val config = Main.instance!!.config

        for (target in Bukkit.getOnlinePlayers()) {
            target.inventory.clear()
            target.teleport(RocketSpleefEssentials.getMapSpawn(map))
            val Bow = ItemStack(Material.BOW)
            val meta = Bow.itemMeta
            meta.setCustomModelData(1234)
            meta.displayName(Component.text("Rocket Launcher"))
            meta.isUnbreakable = false
            meta.addEnchant(Enchantment.ARROW_INFINITE,1, true)
            Bow.setItemMeta(meta)
            target.inventory.addItem(Bow)
            target.inventory.addItem(ItemStack(Material.ARROW))
        }

        RocketSpleefEssentials.setGameActive(true)

        RocketSpleefEssentials.setCountdownActive(true)

        var time = 10
        player.sendMessage("§bCountdown has been started!")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    target.gameMode = GameMode.ADVENTURE
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

                        RocketSpleefEssentials.setCountdownActive(false)

                        ParkourStartCommand.start(player)


                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }

}