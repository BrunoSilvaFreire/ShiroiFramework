package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.message.SimpleMessageManager
import me.ddevil.shiroi.craft.message.VariableProvider
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.command.CommandSender

abstract class AbstractLangMessageManager<in L : Lang<*>>
@JvmOverloads
constructor(
        plugin: ShiroiPlugin<*, *>,
        messageSeparator: String,
        pluginPrefix: String,
        providers: List<VariableProvider> = emptyList()
) : SimpleMessageManager(plugin, messageSeparator, pluginPrefix, providers), LangMessageManager<L> {
    override fun sendMessage(sender: CommandSender, message: L) {
        sendMessage(sender, message, *emptyArray<MessageVariable>())
    }

    override fun sendMessage(sender: CommandSender, message: L, vararg variables: MessageVariable) {
        sendMessage(sender, message.translate(getLang(message), *variables))
    }
}