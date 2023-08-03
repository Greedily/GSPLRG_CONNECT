package de.mrlauchi.gsplrg_connectpaper.acerace.Listeners

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.util.Vector

class AceRaceJumpListener : Listener {
    @EventHandler
    fun onjump(event: PlayerJumpEvent) {
        val player = event.player
        val config = Main.instance!!.config
        val loc = player.location
        loc.y -= 1
        if (loc.block.blockData.material == Material.RED_CONCRETE && AceRaceEssentials.getActive() == 1) {
           // player.velocity = player.location.direction.multiply(3)
            //player.velocity = Vector(player.location.direction.x, player.location.direction.y * 3, player.location.direction.z) // 25 is a lot FUNE TEST why would it not work
            //player.velocity = player.location.direction.multiply(10).setY(1);
            player.velocity = Vector(player.location.direction.x * 1, 1.5, player.location.direction.z * 1)
        }
    }

}