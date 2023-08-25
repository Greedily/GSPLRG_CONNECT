package de.mrlauchi.gsplrg_connectpaper.deathmatch.listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.deathmatch.other.DeathmatchEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsEssentials
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent

class DeathmatchDeathListener : Listener {
    @EventHandler
    fun onkill (event: PlayerDeathEvent){
        val killer = event.entity.killer as Player
        if (killer !is Player) return
        val config = Main.instance!!.config
        if (config.getInt("deathmatch.gamemodeactive") == 1) {
            pointsEssentials.addplayerpoints(killer, pointsModule.deathmatch.kill)
            DeathmatchEssentials.giveplayerstreak(killer)
            DeathmatchEssentials.resetplayerstreak(event.player)
            val score = "deathmatch.score.${killer.name}" // it sets *score* as the points thing but uses *points* to get the score in the gametime
            config.set(score, config.getInt(score) + 1)
            Main.instance!!.saveConfig()
            if (DeathmatchEssentials.streaks[event.player.killer?.name] == 5){
                Bukkit.broadcastMessage("${event.player.killer} has a MAGNIFICENT 5 kill streak!")
            }
            event.deathMessage = null
            //killer.sendMessage("You killed a player! +" + config.getInt("deathmatch.score.$killer").toString()) // i dont remember what that was for alr look at dc nothing there
            //Bukkit.broadcastMessage("${killer.name} killed a player!")
        }
    }
}