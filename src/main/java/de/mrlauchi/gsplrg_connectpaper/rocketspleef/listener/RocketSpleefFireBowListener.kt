package de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityInteractEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class RocketSpleefFireBowListener : Listener {
    @EventHandler
    fun onfire(event: PlayerInteractEvent){
        val bow : ItemStack = event.player.itemInHand ?: return

        if (bow.itemMeta?.hasCustomModelData() == false) return
        if (bow.itemMeta.customModelData != 1234) return
        val player = event.player
        event.isCancelled = true

        val fireball = player.launchProjectile(Fireball::class.java, player.location.direction.multiply(5))
        fireball.shooter = player
    }
}