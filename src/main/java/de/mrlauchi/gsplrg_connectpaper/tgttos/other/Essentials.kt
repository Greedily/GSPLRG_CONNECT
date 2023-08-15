package de.mrlauchi.gsplrg_connectpaper.tgttos.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object Essentials {

    var currentplacement  = 0

    var endmsg = listOf<String?>()

    public fun getSpawnLoc(name: String): Location {
        val config = Main.instance!!.config

        val x = config.getDouble("tgttos.$name.spawn.x")
        val y = config.getDouble("tgttos.$name.spawn.y")
        val z = config.getDouble("tgttos.$name.spawn.z")
        val worldName = config.getString("tgttos.$name.spawn.world")
        val world = Bukkit.getWorld(worldName!!)

        return Location(world, x, y, z)
    }

    public fun setSpawnLoc(name: String, player: Player) {
        val config = Main.instance!!.config

        val x = player.location.x
        val y = player.location.y
        val z = player.location.z
        val world = player.location.world!!.name.toString()

        config.set("tgttos.$name.spawn.x", x)
        config.set("tgttos.$name.spawn.y", y)
        config.set("tgttos.$name.spawn.z", z)

        config.set("tgttos.$name.spawn.world", world)

        Main.instance!!.saveConfig()

        return
    }




    public fun getEndLoc(name: String): Location {
        val config = Main.instance!!.config

        val x = config.getDouble("tgttos.$name.end.x")
        val y = config.getDouble("tgttos.$name.end.y")
        val z = config.getDouble("tgttos.$name.end.z")
        val worldName = config.getString("tgttos.$name.end.world")
        val world = Bukkit.getWorld(worldName!!)

        return Location(world, x, y, z)
    }

    public fun setEndLoc(name: String, player: Player) {
        val config = Main.instance!!.config

        val pitch = player.location.pitch
        val yaw = player.location.yaw

        val x = player.location.x
        val y = player.location.y
        val z = player.location.z
        val world = player.location.world!!.name.toString()

        config.set("tgttos.$name.end.x", x)
        config.set("tgttos.$name.end.y", y)
        config.set("tgttos.$name.end.z", z)

        config.set("tgttos.$name.end.world", world)

        Main.instance!!.saveConfig()

        return
    }

    fun getActive() : String? {
        val config = Main.instance!!.config
        return config.getString("tgttos.active")
    }

    fun setActive(name : String?) {
        val config = Main.instance!!.config

        config.set("tgttos.active", name)

        Main.instance!!.saveConfig()

        return
    }

    fun getCountdownActive() : Int {
        val config = Main.instance!!.config

        if(config.get("tgttos.countdown") != 1) {
            return 0
        }

        return 1
    }

    fun setCountdownActive(int : Int) {
        val config = Main.instance!!.config

        config.set("tgttos.countdown", int)

        Main.instance!!.saveConfig()
        return
    }

    fun startTimer(){
        val config = Main.instance!!.config
        config.set("tgttos.timeractive",1)

        Main.instance!!.saveConfig()
    }
    fun stopTimer(){
        val config = Main.instance!!.config
        config.set("tgttos.timeractive",0)

        Main.instance!!.saveConfig()
    }

    fun setPlacement(player : Player){
        val config = Main.instance!!.config

        currentplacement += 1
        if (currentplacement < 10){
            if (currentplacement == 1){
                endmsg += " §l${currentplacement}st:§r §6${player.name}§r with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 2){
                endmsg += " §l${currentplacement}nd:§r §9${player.name}§r with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 3){
                endmsg += " §l${currentplacement}rd:§r §a${player.name}§r with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement > 3){
                endmsg += " §l${currentplacement}th:§r ${player.name} with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
        }else{
            if (currentplacement != 21 && currentplacement != 22 && currentplacement != 23){
                endmsg += " §l${currentplacement}th:§r ${player.name} with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 21){
                endmsg += " §l${currentplacement}st:§r ${player.name} with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 22){
                endmsg += " §l${currentplacement}nd:§r ${player.name} with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 23){
                endmsg += " §l${currentplacement}rd:§r ${player.name} with time ${config.getString("tgttos.playertimes.${player.name}")}(${pointsModule.tgttos.placementlist[currentplacement]} extra points)"
            }
        }

    }

    fun resetplacements(){
        endmsg = listOf<String?>()
        currentplacement = 0
    }

}