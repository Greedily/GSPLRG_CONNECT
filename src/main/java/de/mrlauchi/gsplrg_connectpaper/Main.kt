package de.mrlauchi.gsplrg_connectpaper

import de.mrlauchi.gsplrg_connectpaper.acerace.Commands.AceRaceCommand
import de.mrlauchi.gsplrg_connectpaper.acerace.Listeners.AceRaceDeathListener
import de.mrlauchi.gsplrg_connectpaper.acerace.Listeners.AceRaceJumpListener
import de.mrlauchi.gsplrg_connectpaper.acerace.Listeners.AceRaceMoveListener
import de.mrlauchi.gsplrg_connectpaper.acerace.Listeners.AceRaceRespawnListener
import de.mrlauchi.gsplrg_connectpaper.deathmatch.commands.DeathmatchCommand
import de.mrlauchi.gsplrg_connectpaper.deathmatch.listeners.DeathmatchDeathListener
import de.mrlauchi.gsplrg_connectpaper.deathmatch.listeners.DeathmatchMoveListener
import de.mrlauchi.gsplrg_connectpaper.deathmatch.listeners.DeathmatchRespawnListener
import de.mrlauchi.gsplrg_connectpaper.goldrush.commands.GoldRushCommand
import de.mrlauchi.gsplrg_connectpaper.goldrush.listener.GoldRushPlayerMoveListener
import de.mrlauchi.gsplrg_connectpaper.goldrush.listener.GoldRushRespawnListener
import de.mrlauchi.gsplrg_connectpaper.hungergames.commands.HungergamesCommand
import de.mrlauchi.gsplrg_connectpaper.hungergames.listeners.HungergamesDeathListener
import de.mrlauchi.gsplrg_connectpaper.hungergames.listeners.HungergamesMoveListener
import de.mrlauchi.gsplrg_connectpaper.hungergames.listeners.HungergamesRespawnListener
import de.mrlauchi.gsplrg_connectpaper.hungergames.listeners.SkywarsDeathListener
import de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourCommand
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourDamageListener
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourDeathListener
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourMoveListener
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourRespawnListener
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.commands.RocketSpleefCommand
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener.RocketSpleefFireBowListener
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener.RocketSpleefMoveListener
import de.mrlauchi.gsplrg_connectpaper.skywars.commands.SkywarsCommand
import de.mrlauchi.gsplrg_connectpaper.skywars.listeners.SkywarsMoveListener
import de.mrlauchi.gsplrg_connectpaper.skywars.listeners.SkywarsRespawnListener
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.TGTTOSCommand
import de.mrlauchi.gsplrg_connectpaper.tgttos.listener.DeathListener
import de.mrlauchi.gsplrg_connectpaper.tgttos.listener.MovementListener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    fun WW1() = "ww1"
    fun PZ() = "PZ"
    fun TH() = "th"

    companion object {
        var instance: Main? = null
            private set;
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this

        getCommand("tgttos")?.setExecutor(TGTTOSCommand())
        getCommand("tgttos")?.tabCompleter = TGTTOSCommand()

        getCommand("parkour")?.setExecutor(ParkourCommand())
        getCommand("parkour")?.tabCompleter = ParkourCommand()

        getCommand("goldrush")?.setExecutor(GoldRushCommand())
        getCommand("goldrush")?.tabCompleter = GoldRushCommand()

        getCommand("skywars")?.setExecutor(SkywarsCommand())
        getCommand("skywars")?.tabCompleter = SkywarsCommand()

        getCommand("hungergames")?.setExecutor(HungergamesCommand())
        getCommand("hungergames")?.tabCompleter = HungergamesCommand()

        getCommand("deathmatch")?.setExecutor(DeathmatchCommand())
        getCommand("deathmatch")?.tabCompleter = DeathmatchCommand()

        getCommand("acerace")?.setExecutor(AceRaceCommand())
        getCommand("acerace")?.tabCompleter = AceRaceCommand()

        getCommand("rocketspleef")?.setExecutor(RocketSpleefCommand())
        getCommand("rocketspleef")?.tabCompleter = RocketSpleefCommand()

        val pluginManager = server.pluginManager

        // TGTTOS Listeners
        pluginManager.registerEvents(DeathListener(), this)
        pluginManager.registerEvents(MovementListener(), this)

        // Parkour Listeners
        pluginManager.registerEvents(ParkourDamageListener(), this)
        pluginManager.registerEvents(ParkourDeathListener(), this)
        pluginManager.registerEvents(ParkourMoveListener(), this)
        pluginManager.registerEvents(ParkourRespawnListener(), this)

        // GoldRush Listeners e
        pluginManager.registerEvents(GoldRushPlayerMoveListener(), this)
        pluginManager.registerEvents(GoldRushRespawnListener(), this)

        // SkyWars Listeners
        pluginManager.registerEvents(SkywarsMoveListener(),this)
        pluginManager.registerEvents(SkywarsDeathListener(),this)
        pluginManager.registerEvents(SkywarsRespawnListener(), this)

        //HungerGames Listeners
        pluginManager.registerEvents(HungergamesDeathListener(), this)
        pluginManager.registerEvents(HungergamesMoveListener(), this)
        pluginManager.registerEvents(HungergamesRespawnListener(),this)

        //DeathMatch Listeners
        pluginManager.registerEvents(DeathmatchMoveListener(), this)
        pluginManager.registerEvents(DeathmatchRespawnListener(), this)
        pluginManager.registerEvents(DeathmatchDeathListener(), this)

        //AceRace Listeners
        pluginManager.registerEvents(AceRaceMoveListener(), this)
        pluginManager.registerEvents(AceRaceDeathListener(), this)
        pluginManager.registerEvents(AceRaceRespawnListener(), this)
        //pluginManager.registerEvents(AceRaceJumpListener(), this)

        //RocketSpleef Listeners
        pluginManager.registerEvents(RocketSpleefMoveListener(), this)
        pluginManager.registerEvents(RocketSpleefFireBowListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}