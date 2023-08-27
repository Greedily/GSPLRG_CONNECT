package de.mrlauchi.gsplrg_connectpaper.other

import org.bukkit.Bukkit
import org.bukkit.GameMode

object GameUtil {

    fun getRemainPlayers(): List<String?>{
        val remainingPlayers = mutableListOf<String?>()
        for (target in Bukkit.getOnlinePlayers()){
            if (target.gameMode == GameMode.ADVENTURE){
                remainingPlayers += target.name
            }
        }
        return remainingPlayers
    }

}