package de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class RocketSpleefFireBowListener : Listener {
    @EventHandler
    fun onfire(event: EntityShootBowEvent){
        val bow = event.bow ?: return
        if (event.entity !is Player) return
        if (bow.itemMeta?.hasCustomModelData() == false) return
        if (bow.itemMeta.customModelData != 1234) return
        val player = event.entity
        event.isCancelled = true

        val fireball = player.launchProjectile(Fireball::class.java, event.projectile.velocity)
        fireball.shooter = player
    }
}