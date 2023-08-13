package de.mrlauchi.gsplrg_connectpaper.points.Other

import de.mrlauchi.gsplrg_connectpaper.Main
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

object pointsEssentials {
    fun setplayerpoints(player: Player, points : Int){
        val config = Main.instance!!.config
        val playerpoint = "playerpoints.${player.name}"
        config.set(playerpoint, points)
        Main.instance!!.saveConfig()
    }

    fun getplayerpoints(player: Player) : Int{
        val config = Main.instance!!.config
        val playerpoint = "playerpoints.${player.name}"
        return config.getInt(playerpoint)
    }

    fun addplayerpoints(player: Player, addedpoints : Int){
        val config = Main.instance!!.config
        val playerpoint = "playerpoints.${player.name}"
        config.set(playerpoint, config.getInt(playerpoint) + addedpoints)
        Main.instance!!.saveConfig()
    }

    fun removeplayerpoints(player: Player, removedpoints : Int){
        val config = Main.instance!!.config
        val playerpoint = "playerpoints.${player.name}"
        config.set(playerpoint, config.getInt(playerpoint) - removedpoints)
        Main.instance!!.saveConfig()
    }

    fun resetallplayerpoints(){
        val config = Main.instance!!.config
       for (player in Bukkit.getOnlinePlayers()){
           config.set("playerpoints.${player.name}", 0)
       }
        Main.instance!!.saveConfig()
    }

    fun resetteampoints(){
        val config = Main.instance!!.config
        for (team in pointsModule.teams){
            config.set("teampoints.${team}",0)

        }
        Main.instance!!.saveConfig()
    }

    fun updateteampoints(){
        val config = Main.instance!!.config
        for (player in Bukkit.getOnlinePlayers()){
            val plrteampoints = "teampoints.${player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(player.name))!!.name}"
            val plrpoints = "playerpoints.${player.name}"
            config.set(plrteampoints, 0)
            config.set(plrteampoints, config.getInt(plrteampoints) + config.getInt(plrpoints))
        }
        Main.instance!!.saveConfig()
    }

    fun getteampoints(player: Player) : Int{
        val config = Main.instance!!.config
        val teamname = player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(player.name))!!.name
        val teampoint = "playerpoints.${teamname}"
        return config.getInt(teampoint)

    }
}