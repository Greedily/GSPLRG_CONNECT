package de.mrlauchi.gsplrg_connectpaper.goldrush.commands

import de.mrlauchi.gsplrg_connectpaper.goldrush.other.GoldRushEssentials

object StopCommand {

    fun stop() {
        GoldRushEssentials.setActive(0)
        GoldRushEssentials.stopTimer()
    }
}