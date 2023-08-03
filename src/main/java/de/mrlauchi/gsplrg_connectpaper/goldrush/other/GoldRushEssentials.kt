package de.mrlauchi.gsplrg_connectpaper.goldrush.other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object GoldRushEssentials {

    fun setCoords(player: Player, team : String){
        val oppos = player.location
        val config = Main.instance!!.config

        config.set("goldrush.spawn.$team.X", oppos.x)
        config.set("goldrush.spawn.$team.Y", oppos.y)
        config.set("goldrush.spawn.$team.Z", oppos.z)

        config.set("goldrush.spawn.$team.world", oppos.world!!.name)

        Main.instance!!.saveConfig()

        player.sendMessage("§bSet spawn for $team to your current location!")

    }
    fun getCoords(team : String): Location {
        val config = Main.instance!!.config

        val x = config.getDouble("goldrush.spawn.$team.X")
        val y = config.getDouble("goldrush.spawn.$team.Y")
        val z = config.getDouble("goldrush.spawn.$team.Z")

        val worldName = config.getString("goldrush.spawn.$team.world")
        val world = Bukkit.getWorld(worldName!!)

        return Location(world, x, y, z)
    }

    fun getCountdownActive() : Int {
        val config = Main.instance!!.config

        if(config.get("goldrush.countdown") != 1) {
            return 0
        }

        return 1
    }

    fun setCountdownActive(int : Int) {
        val config = Main.instance!!.config

        config.set("goldrush.countdown", int)

        Main.instance!!.saveConfig()
        return
    }


    fun stopTimer() {
        val config = Main.instance!!.config

        config.set("goldrush.stoptimer", 1)

        Main.instance!!.saveConfig()
    }

    fun isActive(): Boolean {
        val config = Main.instance!!.config
        val status = config.getInt("goldrush.active")

        if(status != 1) {
            return false
        }

        return true
    }

    fun setActive(newStatus : Int) {
        val config = Main.instance!!.config

        config.set("goldrush.active", newStatus)
        Main.instance!!.saveConfig()
    }

    fun hasCoords(team: String): Boolean {
        val config = Main.instance!!.config
        if(config.get("goldrush.spawn.$team.X") != null) {
            return true
        }

        return false
    }

    fun getKitItem(material: Material, unbreakable : Boolean, amount : Int): ItemStack {
        val result = ItemStack(material)
        result.amount = amount
        val resultMeta = result.itemMeta

        resultMeta!!.isUnbreakable = unbreakable

        result.itemMeta = resultMeta

        return result
    }

}