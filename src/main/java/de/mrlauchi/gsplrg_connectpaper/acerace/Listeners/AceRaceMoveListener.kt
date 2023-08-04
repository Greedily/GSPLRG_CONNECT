package de.mrlauchi.gsplrg_connectpaper.acerace.Listeners

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.acerace.other.AceRaceEssentials
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.Potion
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector

class AceRaceMoveListener : Listener {
    @EventHandler
    fun  onMove(event: PlayerMoveEvent){
        val config = Main.instance!!.config
        if (AceRaceEssentials.getActive() == 0) return

        val player = event.player

        //CountDown.
        if(config.getInt("acerace.countdownactive") == 1) {
            if(event.from.x != event.to?.x || event.from.z != event.to?.z) {
                event.isCancelled = true
            }
        }
        //Main Game.
       // Bukkit.broadcastMessage(event.to?.x.toString()+","+event.to?.y.toString()+","+event.to?.z.toString())
        if(player.gameMode == GameMode.ADVENTURE) {
            if(player.location.y <= -5.0) {
                player.health = 0.0
            }
            player.saturation = 20.0F
            player.foodLevel = 20

            val radius = 5

            for (i in 0..20) {
                if (player.location.distance(AceRaceEssentials.getCoordinate(i)) <= radius) {
                    if(i != AceRaceEssentials.getSection(player)) {
                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)

                        if(i + 1 != 21) {
                            player.sendMessage("§bYou've reached a Checkpoint!")
                        }

                        val location : Location = player.getLineOfSight(null, 2).last().location

                        val fw = player.world.spawnEntity(
                            player.location.add(0.0, 1.0, 0.0),
                            EntityType.FIREWORK
                        ) as Firework
                        val fwm = fw.fireworkMeta
                        fwm.addEffect(FireworkEffect.builder().withColor(Color.GREEN).flicker(false).build())
                        fw.customName = "nodamage"
                        fw.fireworkMeta = fwm
                        fw.detonate()
                    }

                    if (i == 0) { // rotation done.
                        if (AceRaceEssentials.getSection(player) >= 3) {
                            if (config.getInt("acerace.playermaprotations.${player.name}") == 2) { //finish.
                                player.gameMode = GameMode.SPECTATOR
                                val playertime = config.getString("acerace.playertimes.${player.name}")
                                Bukkit.broadcastMessage("§b${player.name} finished in $playertime!")
                                config.set("acerace.playersfinished.${player.name}", 1)
                            }else{
                                config.set("acerace.playermaprotations.${player.name}", config.getInt("acerace.playermaprotations.${player.name}") + 1)
                                Bukkit.broadcastMessage("${player.name} finished a LAP!")
                                config.set("acerace.score.${player.name}",0)
                            }
                            Main.instance!!.saveConfig()
                        }
                    }
                    AceRaceEssentials.setSection(player, i)


                }
                //ELYTRA
                if (player.location.distance(AceRaceEssentials.getElytraGiveCoordinate(i)) <= radius) {
                    //give elytra
                    player.inventory.chestplate = ItemStack(Material.ELYTRA)
                }
                if (player.location.distance(AceRaceEssentials.getElytraRemoveCoordinate(i)) <= radius) {
                    //remove elytra
                    player.inventory.chestplate = ItemStack(Material.AIR)//e
                }
                //SPEED
                if (player.location.distance(AceRaceEssentials.getSpeedGive(i)) <= radius) {
                    player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 7, 3))
                }
                if (player.location.distance(AceRaceEssentials.getSpeedRemove(i)) <= radius) {
                    player.removePotionEffect(PotionEffectType.SPEED)
                }

            }

            val loc = player.location
            loc.y -= 1
            if (event.getTo().getY() > event.getFrom().getY()) { // yellow jump bad
                if (loc.block.blockData.material == Material.YELLOW_CONCRETE && AceRaceEssentials.getActive() == 1) {
                    player.velocity = Vector(0.2, 1.0, 0.0)
                }
                if (loc.block.blockData.material == Material.RED_CONCRETE && AceRaceEssentials.getActive() == 1) {
                    player.velocity = Vector(player.location.direction.x * 1.2, 3.0, player.location.direction.z * 1.2)
                }
                if (loc.block.blockData.material == Material.BEDROCK && AceRaceEssentials.getActive() == 1) {
                    player.velocity = Vector(player.location.direction.x * 1, 1.5, player.location.direction.z * 1)
                }
                if (loc.block.blockData.material == Material.BLUE_CONCRETE && AceRaceEssentials.getActive() == 1) {
                    player.velocity = Vector(player.location.direction.x * 1, 1.5, player.location.direction.z * 1)
                }
                if (loc.block.blockData.material == Material.GRAY_CONCRETE && AceRaceEssentials.getActive() == 1) {
                    player.velocity = Vector(player.location.direction.x * 1.2, 1.25, player.location.direction.z * 1.2)

                }
                if (loc.block.blockData.material == Material.LIME_CONCRETE && AceRaceEssentials.getActive() == 1) {
                    player.velocity = Vector(player.location.direction.x * 1.2, 3.0, player.location.direction.z * 1.2)
                }

            }

        }

    }

}