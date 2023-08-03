package de.mrlauchi.gsplrg_connectpaper.hungergames.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitRunnable

object HungerGamesTime {
    fun StartGameTime(){
        val config = Main.instance!!.config
        var GameTime = 0
        val x = config.getDouble("hungergames.mapspawn.x")
        val z = config.getDouble("hungergames.mapspawn.z")
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"worldborder center $x $z")
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"worldborder set 200")
       // Bukkit.broadcastMessage("world border set to $x $z")

        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                for (target in Bukkit.getOnlinePlayers()) {
                    if (config.getInt("hungergames.gamemodeactive") == 0){
                        target.gameMode = GameMode.SPECTATOR
                        this.cancel()
                        return
                    }

                    target.gameMode = GameMode.ADVENTURE
                    target.health = 20.0
                    target.saturation = 20.0F
                    target.foodLevel = 20



                    target.isInvulnerable = true

                   if (GameTime >= 40 && config.getInt("hungergames.gamemodeactive") == 1){
                       target.isInvulnerable = false
                       Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"worldborder set 20 800")
                       target.sendTitle("GRACE PERIOD OVER!","border is shrinking")
                       this.cancel()
                   }

                }

                GameTime += 1
            }
        }.runTaskTimer(Main.instance!!, 0, 20)
    }
}