package de.mrlauchi.gsplrg_connectpaper.acerace.other

import de.mrlauchi.gsplrg_connectpaper.Main
import de.mrlauchi.gsplrg_connectpaper.other.PlayerUtil
import de.mrlauchi.gsplrg_connectpaper.other.Spawn
import de.mrlauchi.gsplrg_connectpaper.points.Other.pointsModule
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object AceRaceEssentials {
    var currentplacement  = 0

    var endmsg = listOf<String?>()
    fun setStartCoords(player: Player){

        val config = Main.instance!!.config

        config.set("acerace.spawn.x", player.location.x)
        config.set("acerace.spawn.y", player.location.y)
        config.set("acerace.spawn.z", player.location.z)

        config.set("acerace.spawn.world", player.location.world!!.name)

        Main.instance!!.saveConfig()
        player.sendMessage("SetSpawn Success")
        return
    }
    fun getStartCoords(team: String): Location {
        val config = Main.instance!!.config
        val x = config.getDouble("acerace.spawn.x")
        val y = config.getDouble("acerace.spawn.y")
        val z = config.getDouble("acerace.spawn.z")

        val world = Bukkit.getWorld("world")
        return Location(world, x, y, z)
    }

    fun getCoordinate(section: Int): Location {
        val config = Main.instance!!.config

        val x = config.getDouble("acerace.$section.x")
        val y = config.getDouble("acerace.$section.y")
        val z = config.getDouble("acerace.$section.z")

        val world = Bukkit.getWorld("world")

        return Location(world, x, y, z)
    }
    fun setCoordinate(section: Int, player: Player) {
        val config = Main.instance!!.config

        config.set("acerace.$section.x", player.location.x)
        config.set("acerace.$section.y", player.location.y)
        config.set("acerace.$section.z", player.location.z)

        config.set("acerace.$section.world", player.location.world!!.name)
        player.sendMessage("SetCoordinate Successful!")
        Main.instance!!.saveConfig()

        return
    }

    fun getActive(): Int {
        val config = Main.instance!!.config

        return config.getInt("acerace.gamemodeactive")
    }

    fun setSection(player: Player, section: Int) {
        val config = Main.instance!!.config

        config.set("acerace.score.${player.name}", section)

        Main.instance!!.saveConfig()
        return
    }
    fun getSection(player: Player): Int {
        val config = Main.instance!!.config

        return config.getInt("acerace.score.${player.name}")
    }

    fun setGameModeEnabled(value: Boolean){
        val config = Main.instance!!.config

        if (value){ // Enable Game Mode
            config.set("acerace.gamemodeactive",1)
            Bukkit.getWorld("world")!!.setGameRuleValue("keepInventory", "true")
            Bukkit.getWorld("world")!!.setGameRuleValue("fallDamage", "false")

        }else{
            config.set("acerace.gamemodeactive",0)

            Bukkit.getWorld("world")!!.setGameRuleValue("keepInventory", "true")
            Bukkit.getWorld("world")!!.setGameRuleValue("fallDamage", "false")

            Spawn.teleport(true)
            Main.instance!!.saveConfig()
            resetplacements()
            for (player in Bukkit.getOnlinePlayers()) {
                PlayerUtil.clear(player)
                PlayerUtil.resetVisVul(player)
            }
        }
        Main.instance!!.saveConfig()
    }
    fun setCountDownEnabled(value: Boolean){
        val config = Main.instance!!.config

        if (value){ // Enable Countdown
            config.set("acerace.countdownactive",1)
        }else{
            config.set("acerace.countdownactive",0)
        }
        Main.instance!!.saveConfig()
    }

    fun startTimer(){
        val config = Main.instance!!.config
        config.set("acerace.timeractive",1)

        Main.instance!!.saveConfig()
    }
    fun stopTimer(){
        val config = Main.instance!!.config
        config.set("acerace.timeractive",0)

        Main.instance!!.saveConfig()
    }

    fun setElytraGiveCoordinate(section: Int, player: Player) {
        val config = Main.instance!!.config

        config.set("acerace.elytra.$section.x", player.location.x)
        config.set("acerace.elytra.$section.y", player.location.y)
        config.set("acerace.elytra.$section.z", player.location.z)

        config.set("acerace.elytra.$section.world", player.location.world!!.name)
        player.sendMessage("SetCoordinate Successful!")
        Main.instance!!.saveConfig()
    }

    fun getElytraGiveCoordinate(section: Int): Location {
        val config = Main.instance!!.config

        val x = config.getDouble("acerace.elytra.$section.x")
        val y = config.getDouble("acerace.elytra.$section.y")
        val z = config.getDouble("acerace.elytra.$section.z")

        val world = Bukkit.getWorld("world")

        return Location(world, x, y, z)
    }

    fun setElytraRemoveCoordinate(section: Int, player: Player) {
        val config = Main.instance!!.config

        config.set("acerace.elytra.remove.$section.x", player.location.x)
        config.set("acerace.elytra.remove.$section.y", player.location.y)
        config.set("acerace.elytra.remove.$section.z", player.location.z)

        config.set("acerace.elytra.remove.$section.world", player.location.world!!.name)
        player.sendMessage("SetCoordinate Successful!")
        Main.instance!!.saveConfig()
    }

    fun getElytraRemoveCoordinate(section: Int): Location {//e
        val config = Main.instance!!.config

        val x = config.getDouble("acerace.elytra.remove.$section.x")
        val y = config.getDouble("acerace.elytra.remove.$section.y")
        val z = config.getDouble("acerace.elytra.remove.$section.z")

        val world = Bukkit.getWorld("world")

        return Location(world, x, y, z)
    }
    fun setSpeedGive(section: Int, player: Player) {
        val config = Main.instance!!.config

        config.set("acerace.speed.$section.x", player.location.x)
        config.set("acerace.speed.$section.y", player.location.y)
        config.set("acerace.speed.$section.z", player.location.z)

        player.sendMessage("SetCoordinate Successful!")
        Main.instance!!.saveConfig()
    }

    fun getSpeedGive(section: Int): Location {//e
        val config = Main.instance!!.config

        val x = config.getDouble("acerace.speed.$section.x")
        val y = config.getDouble("acerace.speed.$section.y")
        val z = config.getDouble("acerace.speed.$section.z")

        val world = Bukkit.getWorld("world")

        return Location(world, x, y, z)
    }

    fun setSpeedRemove(section: Int, player: Player) {
        val config = Main.instance!!.config

        config.set("acerace.speed.remove.$section.x", player.location.x)
        config.set("acerace.speed.remove.$section.y", player.location.y)
        config.set("acerace.speed.remove.$section.z", player.location.z)

        player.sendMessage("SetCoordinate Successful!")
        Main.instance!!.saveConfig()
    }

    fun getSpeedRemove(section: Int): Location {//e
        val config = Main.instance!!.config

        val x = config.getDouble("acerace.speed.remove.$section.x")
        val y = config.getDouble("acerace.speed.remove.$section.y")
        val z = config.getDouble("acerace.speed.remove.$section.z")

        val world = Bukkit.getWorld("world")

        return Location(world, x, y, z)
    }

    fun setPlacement(player : Player){
        val config = Main.instance!!.config

        currentplacement += 1
        if (currentplacement < 10){
            if (currentplacement == 1){
                endmsg += " §l${currentplacement}st:§r §6${player.name}§r with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 2){
                endmsg += " §l${currentplacement}nd:§r §9${player.name}§r with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 3){
                endmsg += " §l${currentplacement}rd:§r §a${player.name}§r with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement > 3){
                endmsg += " §l${currentplacement}th:§r ${player.name} with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
        }else{
            if (currentplacement != 21 && currentplacement != 22 && currentplacement != 23){
                endmsg += " §l${currentplacement}th:§r ${player.name} with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 21){
                endmsg += " §l${currentplacement}st:§r ${player.name} with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 22){
                endmsg += " §l${currentplacement}nd:§r ${player.name} with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
            if (currentplacement == 23){
                endmsg += " §l${currentplacement}rd:§r ${player.name} with time ${config.getString("acerace.playertimes.${player.name}")}(${pointsModule.acerace.placementlist[currentplacement]} extra points)"
            }
        }

    }

    fun resetplacements(){
        endmsg = listOf<String?>()
        currentplacement = 0
    }

}