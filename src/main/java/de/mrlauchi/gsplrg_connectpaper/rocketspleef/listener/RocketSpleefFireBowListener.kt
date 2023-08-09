package de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener

import de.mrlauchi.gsplrg_connectpaper.rocketspleef.other.RocketSpleefEssentials
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
        val hand : ItemStack = event.player.itemInHand ?: return

        if (event.action.isRightClick) return
        if(RocketSpleefEssentials.getRocket(event.player) <= 0) return
        if(RocketSpleefEssentials.getGameActive() == 0) return
        if(hand.itemMeta == null) return
        if (hand.itemMeta?.hasCustomModelData() == false) return
        if (hand.itemMeta.customModelData != 1234) return
        val player = event.player
        event.isCancelled = true

        RocketSpleefEssentials.removeRocket(player)

        val fireball: Fireball = player.launchProjectile(Fireball::class.java, player.location.direction.multiply(5))
        fireball.yield = 4F
        fireball.shooter = player
    }
}