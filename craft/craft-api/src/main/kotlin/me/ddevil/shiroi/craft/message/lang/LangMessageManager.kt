package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.config.ConfigValue
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.misc.variable.MessageVariable
import org.bukkit.command.CommandSender

interface LangMessageManager<in V : ConfigValue<String, *>> : MessageManager {

    fun sendMessage(sender: CommandSender, message: Lang<V>)

    fun sendMessage(sender: CommandSender,
                    message: Lang<V>,
                    vararg variables: MessageVariable)

    fun getLang(lang: Lang<V>): String

}