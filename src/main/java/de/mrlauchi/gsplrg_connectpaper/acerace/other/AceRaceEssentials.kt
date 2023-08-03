package de.mrlauchi.gsplrg_connectpaper.acerace.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object AceRaceEssentials {
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
            Bukkit.getWorld("world")!!.setGameRuleValue("fallDamage", "false") //
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
}