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
        }
        RocketSpleefEssentials.setGameActive(true)

        RocketSpleefEssentials.setCountdownActive(true)
        config.set("rocketspleef.currentmap", map)
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
                    //giving items

                    target.sendTitle("§b$time", "")

                    target.isInvisible = true

                    target.isInvulnerable = true

                    if(time <= 0) {
                        target.sendTitle("§bStart!!!", "")

                        //target.isInvisible = false

                        //target.isInvulnerable = false

                        val ELYTRA = ItemStack(Material.ELYTRA)
                        val emeta = ELYTRA.itemMeta
                        emeta.isUnbreakable = true
                        ELYTRA.setItemMeta(emeta)
                        target.inventory.chestplate = ItemStack(ELYTRA)
                        val Bow = ItemStack(Material.DIAMOND_SHOVEL)
                        val meta = Bow.itemMeta
                        meta.setCustomModelData(1234)
                        meta.displayName(Component.text("NUKE (leftclick only)"))
                        meta.isUnbreakable = true
                        meta.addEnchant(Enchantment.ARROW_INFINITE,1, true)
                        Bow.setItemMeta(meta)
                        target.inventory.addItem(Bow)

                        config.set("rocketspleef.rocketslots.${player.name}", 3)
                        config.set("rocketspleef.timer.${player.name}", -1)

                        RocketSpleefEssentials.setCountdownActive(false)

                        ParkourStartCommand.start(player)

                        Main.instance!!.saveConfig()
                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }

}