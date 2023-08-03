package de.mrlauchi.gsplrg_connectpaper.tgttos.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object Essentials {

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

}