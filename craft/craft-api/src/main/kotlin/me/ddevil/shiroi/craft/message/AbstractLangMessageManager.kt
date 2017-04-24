package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.message.lang.Lang
import me.ddevil.shiroi.craft.message.lang.MessageVariable
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack

abstract class AbstractLangMessageManager<P : ShiroiPlugin<*, *>, in L : Lang<*, *>>
@JvmOverloads
constructor(
        plugin: P,
        messageSeparator: String,
        pluginPrefix: String,
        translators: List<TagTranslator> = emptyList()
) : SimpleMessageManager<P>(plugin, messageSeparator, pluginPrefix, translators), LangMessageManager<L> {

    override fun sendMessage(sender: CommandSender, message: L, vararg variables: MessageVariable) {
        sendMessage(sender, message.translate(getLang(message), *variables))
    }

    override fun translateItemStack(itemStack: ItemStack, vararg variables: MessageVariable): ItemStack {
        val meta = itemStack.itemMeta
        if (meta.hasDisplayName()) {
            var name = meta.displayName
            for (variable in variables) {
                name = name.replace(variable.name, variable.value)
            }
            meta.displayName = translateAll(name)
        }
        if (meta.hasLore()) {
            meta.lore = translateAll(meta.lore.map {
                var name = it
                for (variable in variables) {
                    name = name.replace(variable.name, variable.value)
                }
                return@map name
            })
        }
        itemStack.itemMeta = meta
        return translateItemStack(itemStack)
    }

    override fun createItemStack(original: ItemStack,
                                 displayNameLang: L,
                                 lores: Set<L>,
                                 vararg variables: MessageVariable): ItemStack {
        val meta = original.itemMeta
        meta.displayName = translateAll(displayNameLang.translate(getLang(displayNameLang), *variables))
        meta.lore = lores.map {
            translateAll(displayNameLang.translate(getLang(it), *variables))
        }
        original.itemMeta = meta
        return original
    }
}