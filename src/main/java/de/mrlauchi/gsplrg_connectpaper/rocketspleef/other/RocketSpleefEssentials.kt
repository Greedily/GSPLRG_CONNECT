package de.mrlauchi.gsplrg_connectpaper.rocketspleef.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

object RocketSpleefEssentials {

    var currentteamplacement  = 0

    var endmsg = listOf<String?>()

    val slots = mutableMapOf<String, Int>()
    val times = mutableMapOf<String, Int>()

    var teams = listOf<String>()
    fun setActive(value : Boolean){
        val config = Main.instance!!.config
        if (value) {
            config.set("rocketspleef.gamemodeactive",1)
            for (player in Bukkit.getOnlinePlayers()){
                slots.put(player.name, 3)
                times.put(player.name, 4)
                //what time do we give to the palyer
            }
            for (target in Bukkit.getOnlinePlayers()) {
                val targetteam = target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
                if (!teams.contains(targetteam)) { // add all the alive teams into the list.
                    currentteamplacement +=1
                    teams += targetteam
                }
            }
        }else{
            config.set("rocketspleef.gamemodeactive",0)
            resetplacements()
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
    fun setMapSpawn(name: String, player: Player){
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

    fun setTimerActive(v: Boolean){
        val config = Main.instance!!.config
        if (v == true) {
            config.set("rocketspleef.timeractive", 1)
        }else{
            config.set("rocketspleef.timeractive", 0)
        }
        Main.instance!!.saveConfig()
    }

    fun sendRocketSlot(target : Player, rockets : Int){
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
       // val config = Main.instance!!.config
       // var old_value = config.getInt("rocketspleef.rocketslots.${player.name}")
        val old_value = slots[player.name]!!
        val new_value = old_value + 1
        if (old_value != 3) {
           // config.set("rocketspleef.rocketslots.${player.name}", new_value)
            //slots[player.name] == new_value
            slots.remove(player.name)
            slots.put(player.name, new_value)
        }

        sendRocketSlot(player,new_value)

        Main.instance!!.saveConfig()
    }

    fun removeRocket(player: Player) {
        //val config = Main.instance!!.config

        //var old_value = config.getInt("rocketspleef.rocketslots.${player.name}")
        val old_value = slots[player.name]!!
        val new_value = old_value - 1
       // config.set("rocketspleef.rocketslots.${player.name}", new_value)
       // slots[player.name] == new_value
        slots.remove(player.name)
        slots.put(player.name, new_value)

        sendRocketSlot(player, new_value)

       // Main.instance!!.saveConfig()
    }

    fun getRocket(player: Player): Int {
        var playeravailable = false
       for (plr in slots){
           if (plr.key == player.name){
               playeravailable = true
           }
       }
        return slots[player.name]!!
    }

    fun getTime(player: Player): Int {
        //val config = Main.instance!!.config

        //return config.getInt("rocketspleef.timer.${player.name}")
        return times[player.name]!!
    }

    fun setTime(player: Player, int: Int) {
      //  val config = Main.instance!!.config

       // config.set("rocketspleef.timer.${player.name}", int)
        //Main.instance!!.saveConfig()
        //times[player.name] == int
        times.remove(player.name)
        times.put(player.name, int)
        return
    }


    fun rocketReload(){
        // if the slots arent filled (isnt 3) refill it with a timer,

        // everu second give players one slot if its not fillled
        val bukkitRunnable = object: BukkitRunnable(){
            override fun run() {
                if (getActive() == 0) {
                    this.cancel()

                    return//e
                }
                for (target in Bukkit.getOnlinePlayers()){
                    val config = Main.instance!!.config


                    //give them a point
                    if(getRocket(target) != 3) { // if not all rockets slots full
                        if(getTime(target) > 0 && getTime(target) < 5) { //if player's time >0 and timer is smaller than 3
                            setTime(target, getTime(target).minus(1)) // minus one for it

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


    fun setteamPlacement(team : String){
        val config = Main.instance!!.config
        var totalteamminutetimes = 0
        for (target in Bukkit.getOnlinePlayers()){
            val targetteam = target.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(target.name))!!.name
            if (targetteam == team){
                val timestring = config.getString("rocketspleef.playertimes.${target.name}")
                val minutetime : Int = timestring?.split(":")?.get(0)!!.toInt()

                totalteamminutetimes += minutetime
            }
        }
        if (currentteamplacement < 10){
            if (currentteamplacement == 1){
                endmsg += " §l${currentteamplacement}st:§r §6${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 2){
                endmsg += " §l${currentteamplacement}nd:§r §9${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 3){
                endmsg += " §l${currentteamplacement}rd:§r §a${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement > 3){
                endmsg += " §l${currentteamplacement}th:§r ${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
        }else{
            if (currentteamplacement != 21 && currentteamplacement != 22 && currentteamplacement != 23){
                endmsg += " §l${currentteamplacement}th:§r ${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 21){
                endmsg += " §l${currentteamplacement}st:§r ${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 22){
                endmsg += " §l${currentteamplacement}nd:§r ${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
            if (currentteamplacement == 23){
                endmsg += " §l${currentteamplacement}rd:§r ${team}§r with total minutes ${totalteamminutetimes}(${pointsModule.rocketspleef.placementlist[currentteamplacement]} extra points)"
            }
        }
        currentteamplacement -= 1

    }
    fun resetplacements(){
        endmsg = listOf<String?>()
        currentteamplacement = 0
        teams = listOf<String>()
    }
}
