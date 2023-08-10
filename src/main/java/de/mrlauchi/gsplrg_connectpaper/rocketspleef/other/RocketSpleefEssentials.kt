package de.mrlauchi.gsplrg_connectpaper.rocketspleef.other

import de.mrlauchi.gsplrg_connectpaper.Main
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object RocketSpleefEssentials {
    fun setActive(value : Boolean){
        val config = Main.instance!!.config
        if (value) {
            config.set("rocketspleef.gamemodeactive",1)
        }else{
            config.set("rocketspleef.gamemodeactive",0)
        }
        Main.instance!!.saveConfig()
    }
    fun getActive() : Int{
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

    fun giveRocketSlot(target : Player, rockets : Int){
        if(rockets == 0) {
            target.sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§c⬤§c⬤§c⬤")) // how make red
        }
        if(rockets == 1) {
            target.sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§b⬤§c⬤§c⬤")) // how make red
        }
        if(rockets == 2){
            target.sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§b⬤§b⬤§c⬤")) // it does.
        }
        if(rockets == 3){
            target.sendMessage(ChatMessageType.ACTION_BAR, TextComponent("§b⬤§b⬤§b⬤"))
        }
    }


    fun addRocket(player: Player) {
        val config = Main.instance!!.config
        var old_value = config.getInt("rocketspleef.rocketslots.${player.name}")
        val new_value = old_value + 1
        if (old_value != 3) {
            config.set("rocketspleef.rocketslots.${player.name}", new_value)
        }

        giveRocketSlot(player,new_value)

        Main.instance!!.saveConfig()
    }

    fun removeRocket(player: Player) {
        val config = Main.instance!!.config

        var old_value = config.getInt("rocketspleef.rocketslots.${player.name}")
        val new_value = old_value - 1
        config.set("rocketspleef.rocketslots.${player.name}", new_value)

        giveRocketSlot(player, new_value)

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


    fun setTimerActive(v: Boolean){
        val config = Main.instance!!.config
        if (v == true) {
            config.set("rocketspleef.timeractive", 1)
        }else{
            config.set("rocketspleef.timeractive", 0)
        }
        Main.instance!!.saveConfig()
    }

    fun rocketReload(){
        // if the slots arent filled (isnt 3) refill it with a timer,

        // everu second give players one slot if its not fillled
        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                if (getActive() == 0) {
                    this.cancel()

                    return
                }
                for (target in Bukkit.getOnlinePlayers()){
                    val config = Main.instance!!.config


                    //give them a point
                    if(getRocket(target) != 3) { // if not all rockets slots full
                        if(getTime(target) > 0 && getTime(target) < 5) { //if player's time >0 and timer is smaller than 3
                            setTime(target, getTime(target) - 1) // minus one for it

                        }

                        if(getTime(target) == 0) { // if time is 0 give rocket.
                            addRocket(target)
                            setTime(target, 4) // -1 , new: 2

                        }

                       /* if(getTime(target) == -1) {
                            setTime(target, 2)
                        }

                        */
                    }

                }
              Main.instance!!.saveConfig()
            }
        }.runTaskTimer(Main.instance!!, 0, 20)
    }
}
