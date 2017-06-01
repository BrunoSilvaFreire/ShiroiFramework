package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.config.ConfigValue
import me.ddevil.shiroi.craft.message.SimpleMessageManager
import me.ddevil.shiroi.craft.misc.variable.MessageVariable
import me.ddevil.shiroi.craft.misc.variable.VariableProvider
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.command.CommandSender

abstract class AbstractLangMessageManager<in V : ConfigValue<String, *>>
@JvmOverloads
constructor(
        plugin: ShiroiPlugin<*, *>,
        messageSeparator: String,
        pluginPrefix: String,
        providers: List<VariableProvider> = emptyList()
) : SimpleMessageManager(plugin, messageSeparator, pluginPrefix, providers), LangMessageManager<V> {

    override fun sendMessage(sender: CommandSender, message: Lang<V>) {
        sendMessage(sender, message, *emptyArray<MessageVariable>())
    }

    override fun sendMessage(sender: CommandSender, message: Lang<V>, vararg variables: MessageVariable) {
        sendMessage(sender, message.translate(getLang(message), *variables))
    }
}