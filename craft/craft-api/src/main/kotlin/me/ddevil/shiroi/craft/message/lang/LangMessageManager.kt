package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.message.MessageManager
import org.bukkit.command.CommandSender

interface LangMessageManager<in L : Lang<*>> : MessageManager {

    fun sendMessage(sender: CommandSender, message: L)

    fun sendMessage(sender: CommandSender,
                    message: L,
                    vararg variables: MessageVariable)

    fun getLang(lang: L): String

}