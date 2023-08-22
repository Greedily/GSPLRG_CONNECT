package de.mrlauchi.gsplrg_connectpaper.other

import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.entity.Player

object ParticleEssentials {
    fun scoreparticle(player : Player){
        val ploc = player.location

        ploc.world.spawnParticle(Particle.END_ROD, Location(Bukkit.getWorld("world"), ploc.x, ploc.y + 0.5,ploc.z), 50, 0.5,0.5,0.5, 0.1)
    }

    fun getparticlecolor(player: Player): Color{
        val team = player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(player.name))!!.name
        if (team == "orange"){
            return Color.ORANGE
        }
        if (team == "red"){
            return Color.RED
        }
        if (team == "pink"){
            return Color.FUCHSIA
        }
        if (team == "green"){
            return Color.GREEN
        }
        if (team == "blue"){
            return Color.BLUE
        }
        if (team == "lime"){
            return Color.LIME
        }
        if (team == "yellow"){
            return Color.YELLOW
        }
        if (team == "purple"){
            return Color.PURPLE
        }
        return  Color.GREEN
    }

    fun spawnfirework(player : Player){
        val fw = player.world.spawnEntity(
                player.location.add(0.0, 1.0, 0.0),
                EntityType.FIREWORK
        ) as Firework
        val fwm = fw.fireworkMeta
        fwm.addEffect(FireworkEffect.builder().withColor(ParticleEssentials.getparticlecolor(player)).flicker(false).build())
        fw.customName = "nodamage"
        fw.fireworkMeta = fwm
        fw.detonate()
    }
}