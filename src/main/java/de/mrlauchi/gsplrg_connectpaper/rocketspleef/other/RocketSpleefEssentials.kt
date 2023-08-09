package de.mrlauchi.gsplrg_connectpaper.rocketspleef.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourStartCommand
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

object RocketSpleefEssentials {
    fun setGameActive(value : Boolean){
        val config = Main.instance!!.config
        if (value) {
            config.set("rocketspleef.gamemodeactive",1)
        }else{
            config.set("rocketspleef.gamemodeactive",0)
        }
        Main.instance!!.saveConfig()
    }
    fun getGameActive() : Int{
        val config = Main.instance!!.config
        return config.getInt("rocketspleef.gamemodeactive")
    }
    fun setCountdownActive(value : Boolean){
        val config = Main.instance!!.config
        if (value) {
            config.set("rocketspleef.countdownactive",1)
        }else{
            config.set("rocketspleef.countdownactive",0)
        }
        Main.instance!!.saveConfig()
    }
    fun getCountdownActive() : Int{
        val config = Main.instance!!.config
        return config.getInt("rocketspleef.countdownactive")
    }
    fun  setMapSpawn(name: String, player: Player){
        val config = Main.instance!!.config
        config.set("rocketspleef.maps.$name.x", player.location.x)
        config.set("rocketspleef.maps.$name.y", player.location.y)
        config.set("rocketspleef.maps.$name.z", player.location.z)
        Main.instance!!.saveConfig()
    }

    fun getMapSpawn(name : String) : Location{
        val config = Main.instance!!.config
        val x = config.getDouble("rocketspleef.maps.$name.x")
        val y = config.getDouble("rocketspleef.maps.$name.y")
        val z = config.getDouble("rocketspleef.maps.$name.z")

        return Location(Bukkit.getWorld("world"),x,y,z)
    }

    fun addRocket(player: Player) {
        val config = Main.instance!!.config

        var old_value = config.getInt("rocketspleef.rocketslots.${player.name}")
        val new_value = old_value + 1
        if (old_value != 3) {
            config.set("rocketspleef.rocketslots.${player.name}", new_value)
        }

        Main.instance!!.saveConfig()
    }

    fun getRocket(player: Player): Int {
        val config = Main.instance!!.config

        return config.getInt("rocketspleef.rocketslots.${player.name}")
    }

    fun getTime(player: Player): Int {
        val config = Main.instance!!.config

        return config.getInt("rocketspleef.timer.${player.name}")
    }

    fun setTime(player: Player, int: Int) {
        val config = Main.instance!!.config

        config.set("rocketspleef.timer.${player.name}", int)
        Main.instance!!.saveConfig()
        return
    }


    fun rocketReload(){
        // if the slots arent filled (isnt 3) refill it with a timer,

        // everu second give players one slot if its not fillled
        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                if (getGameActive() == 0) return
                for (target in Bukkit.getOnlinePlayers()){
                    //give them a point
                    if(getRocket(target) != 3) {
                        if(getTime(target) > 0 && getTime(target) < 6) {
                            setTime(target, getTime(target) - 1)
                        }

                        if(getTime(target) == 0) {
                            addRocket(target)
                            setTime(target, -1)
                        }
                        if(getTime(target) == -1) {
                            setTime(target, 5)
                        }
                    }

                }
              Main.instance!!.saveConfig()
            }
        }.runTaskTimer(Main.instance!!, 0, 20)
    }
}