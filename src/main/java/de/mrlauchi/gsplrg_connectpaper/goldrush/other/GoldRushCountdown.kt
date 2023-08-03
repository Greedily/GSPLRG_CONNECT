package de.mrlauchi.gsplrg_connectpaper.goldrush.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.goldrush.commands.StartCommand
import de.mrlauchi.gsplrg_connectpaper.other.PasteSchem
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object GoldRushCountdown {

    fun countdown(player: Player) {
        val config = Main.instance!!.config

        config.set("goldrush.stoptimer", 0)
        Main.instance!!.saveConfig()

        GoldRushEssentials.setActive(1)

        for (team in player.scoreboard.teams) {
            if(GoldRushEssentials.hasCoords(team.name)) {
                PasteSchem.paste(GoldRushEssentials.getCoords(team.name), "goldrushconcrete")
                Thread.sleep(30*1000)
            }
        }

        for (target in Bukkit.getOnlinePlayers()) {
            target.inventory.clear()

            target.teleport(GoldRushEssentials.getCoords(target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name))

            target.inventory.addItem(GoldRushEssentials.getKitItem(Material.WOODEN_AXE, true, 1))
            target.inventory.addItem(GoldRushEssentials.getKitItem(Material.WOODEN_SWORD, true, 1))
            target.inventory.addItem(GoldRushEssentials.getKitItem(Material.COOKED_BEEF, true, 32))
        }

        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give @a torch{CanPlaceOn:['minecraft:gray_concrete']} 64")
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "give @a netherite_pickaxe{CanDestroy:['minecraft:gold_block']} 1")

        GoldRushEssentials.setCountdownActive(1)

        var time = 10
        player.sendMessage("§bCountdown has been started!")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    target.gameMode = GameMode.ADVENTURE
                    target.health = 20.0
                    target.saturation = 20.0F
                    target.foodLevel = 20

                    target.sendTitle("", "§b$time")

                    target.isInvisible = true

                    target.isInvulnerable = true


                    if(time <= 0) {
                        target.sendTitle("", "§bStart!!!")

                        target.isInvisible = false

                        target.isInvulnerable = false

                        GoldRushEssentials.setCountdownActive(0)

                        StartCommand.start(player)

                        this.cancel()
                    }
                }

                time -= 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)

    }

}