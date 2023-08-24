package me.tortel.emojis1.Commands

import me.tortel.emojis1.other.EmojiUnicode
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EmojisCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        val Text = TextComponent("Emojis: ")
        val emojiTable = EmojiUnicode.emojiTable
        var finalkeystring = ""
        var firstdone = false
        for (emoji in emojiTable){
            val newtext = TextComponent(" " + emoji.value + " ")
            for (emojeh in emojiTable){
                if (emojeh.key == emoji.key){
                    if (firstdone == true){
                        finalkeystring += " , " + emojeh.key
                    }else{
                        finalkeystring += emojeh.key
                        firstdone = true
                    }
                }
            }
            newtext.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(*TextComponent.fromLegacyText(finalkeystring))))


            Text.addExtra(newtext)
            finalkeystring = ""
            firstdone = false
        }
        sender.sendMessage(Text)




        return false
    }
}