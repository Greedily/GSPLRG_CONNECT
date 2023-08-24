package me.tortel.emojis1.Events

import me.tortel.emojis1.other.EmojiUnicode
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.inventory.InventoryHolder

@Suppress("DEPRECATION")
class OnPlayerChat : Listener {
    @EventHandler
    fun onPlayerChatted(event: PlayerChatEvent) {
        val player = event.player
        val MSG = event.message
        var Format = event.format
        val recipents = event.recipients
        //player.sendMessage("You Typed $MSG, with Format $Format, recipents $recipents")
       // if (!MSG.lowercase().contains(":")) return

       //val newMSG = EmojiUnicode.module.returnMSGwithUniCode(MSG, player)

        val newComponent = EmojiUnicode.returnMSGwithUniCode(MSG, player)
        //var midpart = TextComponent(*TextComponent.fromLegacyText("yeh"))
        //midpart.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(*TextComponent.fromLegacyText("neh"))))

        //player.sendMessage(newMSG)
        // we need to get the first : in the emoji.
        val prefix = player.scoreboard.getPlayerTeam(Bukkit.getOfflinePlayer(player.name))!!.prefix
        var playercomponent = TextComponent("$prefix${player.name}: " )

        if (player.gameMode == GameMode.SPECTATOR){
            playercomponent = TextComponent("\uEE09 $prefix${player.name}: " )
        }

       playercomponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("real name: ImBaldy " +
               "\ntype: Player " +
               "\nid: ${player.uniqueId}}").create())//BaseComponent("{name: ${player.name} , type: $player , id: ${player.uniqueId} }"))

        event.recipients.forEach { recipient ->
            recipient.spigot().sendMessage(playercomponent, newComponent) //midpart , TextComponent(*TextComponent.fromLegacyText("$newMSG"))
        }

        // Cancel the original chat event
        event.isCancelled = true
        //event.setMessage(newMSG)
    }
}
