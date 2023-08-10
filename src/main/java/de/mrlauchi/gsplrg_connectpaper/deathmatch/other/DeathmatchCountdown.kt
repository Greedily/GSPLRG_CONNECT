package de.mrlauchi.gsplrg_connectpaper.deathmatch.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object DeathmatchCountdown {
    fun countdown(player: Player) {
        val config = Main.instance!!.config

        DeathmatchEssentials.setGamemodeEnabled(true)


        for (target in Bukkit.getOnlinePlayers()) {
            target.inventory.clear()

            target.teleport(DeathmatchEssentials.getCoords(target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name))
            config.set("deathmatch.score.${target.name}",0)
            val crossbow = ItemStack(Material.CROSSBOW)
            val itemmeta = crossbow.itemMeta
            itemmeta?.isUnbreakable = true
            itemmeta?.setDisplayName("One Shot Crossbow")
            //itemmeta?.addEnchant(Enchantment.ARROW_DAMAGE, 10, true)
            //itemmeta?.addEnchant(Enchantment.ARROW_INFINITE, 2, true)
            itemmeta?.addEnchant(Enchantment.QUICK_CHARGE, 2, true)
            crossbow.setItemMeta(itemmeta)

            player.inventory.addItem(crossbow)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"give @a tipped_arrow{CustomPotionEffects:[{Id:7,Amplifier:100b,Duration:1}],CustomPotionColor:3538944} 200")

            //give target everything we want to give him.
           // target.inventory.addItem(GoldRushEssentials.getKitItem(Material.WOODEN_AXE, true, 1))
        }


        DeathmatchEssentials.setCountDownEnabled(true)

        var time = 10
        player.sendMessage("§bCountdown started!")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    target.gameMode = GameMode.ADVENTURE
                    target.health = 20.0
                    target.saturation = 20.0F
                    target.foodLevel = 15

                    target.sendTitle("", "§b$time")

                    target.isInvisible = true

                    target.isInvulnerable = true

                    if (config.getInt("deathmatch.countdownactive") == 0){
                        this.cancel()
                    }

                    if(time <= 0) {
                        target.sendTitle("§bStart!!!", "")

                        target.isInvisible = false

                        target.isInvulnerable = false

                        DeathmatchEssentials.setCountDownEnabled(false)
                        DeathmatchEssentials.setGamemodeEnabled(true)
                        DeathmatchGameTime.startGameTime()

                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }
}