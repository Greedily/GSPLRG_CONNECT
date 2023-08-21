package de.mrlauchi.gsplrg_connectpaper

import de.mrlauchi.gsplrg_connectpaper.other.listeners.ItemDropEvent
import de.mrlauchi.gsplrg_connectpaper.acerace.Commands.AceRaceCommand
import de.mrlauchi.gsplrg_connectpaper.acerace.Listeners.AceRaceDeathListener
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
import de.mrlauchi.gsplrg_connectpaper.other.commands.EventCommand
import de.mrlauchi.gsplrg_connectpaper.other.commands.VoteCommand
import de.mrlauchi.gsplrg_connectpaper.other.listeners.InventoryClick
import de.mrlauchi.gsplrg_connectpaper.parkour.commands.ParkourCommand
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourDamageListener
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourDeathListener
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourMoveListener
import de.mrlauchi.gsplrg_connectpaper.parkour.listener.ParkourRespawnListener
import de.mrlauchi.gsplrg_connectpaper.points.Commands.pointsCommand
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.commands.RocketSpleefCommand
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener.RocketSpleefDropListener
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener.RocketSpleefFireBowListener
import de.mrlauchi.gsplrg_connectpaper.rocketspleef.listener.RocketSpleefMoveListener
import de.mrlauchi.gsplrg_connectpaper.skywars.commands.SkywarsCommand
import de.mrlauchi.gsplrg_connectpaper.skywars.listeners.SkywarsMoveListener
import de.mrlauchi.gsplrg_connectpaper.skywars.listeners.SkywarsRespawnListener
import de.mrlauchi.gsplrg_connectpaper.tgttos.commands.TGTTOSCommand
import de.mrlauchi.gsplrg_connectpaper.tgttos.listener.DeathListener
import de.mrlauchi.gsplrg_connectpaper.tgttos.listener.MovementListener
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable



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

        getCommand("vote")?.setExecutor(pointsCommand())
        getCommand("points")?.setExecutor(pointsCommand())

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

        getCommand("event")?.setExecutor(EventCommand())
        getCommand("vote")?.setExecutor(VoteCommand())

        val pluginManager = server.pluginManager

        // PUBLIC LISTENERS
        pluginManager.registerEvents(ItemDropEvent(), this)
        pluginManager.registerEvents(InventoryClick(), this)

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
        pluginManager.registerEvents(RocketSpleefDropListener(), this)

        val config = config

        val bukkitRunnable = object: BukkitRunnable() {
            override fun run() {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendPlayerListHeader(net.kyori.adventure.text.Component.text("\n\uF500\n")) // / n  does this work?
                    val playerpoints = config.get("playerpoints.${player.name}")
                    val teampoints = config.get("teampoints.${player.scoreboard.getPlayerTeam(player)?.name}")
                    player.sendPlayerListFooter(Component.text("§b§lIndividual Points:§6 $playerpoints" +
                                                                        "\n§b§lTeam Points:§6 $teampoints"))
                }

                var points = mutableMapOf<Int, String>()

                for (countTarget in Bukkit.getOnlinePlayers()) {
                    //points.plus(config.getInt("deathmatch.points.${target.name}") to target.name)
                    points += Pair(config.getInt("playerpoints.${countTarget.name}") , countTarget.name)
                }
                points = points.toSortedMap(Comparator.reverseOrder())
                val nameslist = points.values.toList()
                val pointslist = points.keys.toList()

                for (entity in Bukkit.getWorld("world")!!.entities) {
                    if(entity is ArmorStand) {
                        if(entity.scoreboardTags.contains("indivboard")) {

                        }
                        indivboardLogic(1, nameslist, pointslist, entity)
                        indivboardLogic(2, nameslist, pointslist, entity)
                        indivboardLogic(3, nameslist, pointslist, entity)
                        indivboardLogic(4, nameslist, pointslist, entity)
                        indivboardLogic(5, nameslist, pointslist, entity)
                        indivboardLogic(6, nameslist, pointslist, entity)
                        indivboardLogic(7, nameslist, pointslist, entity)
                        indivboardLogic(8, nameslist, pointslist, entity)
                        indivboardLogic(9, nameslist, pointslist, entity)
                        indivboardLogic(10, nameslist, pointslist, entity)
                    }
                }
            }
        }.runTaskTimer(this, 1, 20)


    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun indivboardLogic(placement: Int, nameslist: List<String>, pointslist: List<Int>, entity: ArmorStand) {
        //Bukkit.broadcastMessage("debug 1")
        if(!entity.scoreboardTags.contains("indiv$placement")) return
        //Bukkit.broadcastMessage("debug 2")
        if(nameslist.size < placement) {
            entity.customName = " "
            //Bukkit.broadcastMessage("debug 0")
            return
        }
        /*if(placement == 1) {
            entity.customName = "1st: ${nameslist[0]}: ${pointslist[0]}"
            return
        }
        if(placement == 2) {
            entity.customName = "2nd: ${nameslist[1]}: ${pointslist[1]}"
            return
        }
        if(placement == 3) {
            entity.customName = "3rd: ${nameslist[2]}: ${pointslist[2]}"
            return
        } */
        entity.customName = "${ChatColor.of(Bukkit.getOfflinePlayer(nameslist[placement-1]).player!!.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(nameslist[placement-1]))?.color()!!.asHexString())}${nameslist[placement-1]}: ${pointslist[placement-1]}"
        return
    }

}