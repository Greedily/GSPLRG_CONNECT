package me.tortel.emojis1.other

import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.HoverEvent
import org.bukkit.entity.Player


@Suppress("DEPRECATION")
object EmojiUnicode {


    val emojiTable = mapOf(
            //NOVALY
            ":happy:" to "\uEF00",
            ":laugh:" to "\uEF01",
            ":rofl:" to "\uEF01",
            ":sob:" to "\uEF02",
            ":cry:" to "\uEF02",
            ":cool:" to "\uEF03",
            ":sunglasses:" to "\uEF03",
            ":angry:" to "\uEF04",
            ":monocle:" to "\uEF05",
            ":skull:" to "\uEF07",
            ":pog:" to "\uEF08",
            ":heart:" to "\uEF09",
            ":sad:" to "\uEF0B",
            ":neutral:" to "\uEF0A"
    )


    fun returnMSGwithUniCode(MSG:String, player:Player):TextComponent {

        if (MSG.contains(":")) {

            val words = MSG.split(" ")
            val output = mutableListOf<String>()

            val chatComponents = TextComponent()
            for (word in words) {
                if (emojiTable.containsKey(word.lowercase())) {
                    output.add(emojiTable[word.lowercase()]!!)
                    val textcomponent = TextComponent(emojiTable[word.lowercase()]!! + " ")
                    textcomponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(*TextComponent.fromLegacyText(word))))

                    chatComponents.addExtra(textcomponent)
                } else {
                    output.add(word)
                    val textcomponent = TextComponent(word + " ")

                    chatComponents.addExtra(textcomponent)
                }
            }

            //return output.joinToString(" ")
            return chatComponents

            /*for (e in EmojiTable) {

                if (MSG.contains(":${e.key}:", ignoreCase = true)) {
                    val newMSG = MSG.replace(":${e.key}:","${e.value}", ignoreCase = true)

                    return newMSG
                   */// var test = TextComponent(*TextComponent.fromLegacyText("$newmsg"))
            //test.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(*TextComponent.fromLegacyText("used emojies: :${emoji.key}:"))))


            // val parts = MSG.split(" :", ": ", ":", ignoreCase = true, limit = 3)

            // val firstPart = parts[0] // first part of string
            // val secondPart = parts[1] // part between : (emoji)
            // val thirdPart = parts[2] // the rest of the string

            //var firstpartComponent = TextComponent(firstPart + " ")
            //var thirdpartComponent = TextComponent(" " +thirdPart)
            //var midpart = TextComponent(*TextComponent.fromLegacyText("${emoji.value}"))
            //midpart.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(*TextComponent.fromLegacyText(":$secondPart:"))))
            //var chatcomponent = TextComponent()
            //chatcomponent.addExtra(firstpartComponent)
            // chatcomponent.addExtra(midpart)
            // chatcomponent.addExtra(thirdpartComponent)

            // return (chatcomponent)

            //}

            //}

        }
        return TextComponent(MSG)
    }




}