package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack

abstract class AbstractMessageManager<out P : ShiroiPlugin<*, *>>
(val plugin: P) : MessageManager {
    var colorDesign: PluginColorDesign = plugin.colorDesign
        protected set

    final override fun reload() {
        this.colorDesign = plugin.colorDesign
        reload0()
    }

    open fun reload0() {
    }

    final override fun translateTags(input: Iterable<String>) = input.map { translateTags(it) }

    final override fun translateColors(input: Iterable<String>) = input.map { translateColors(it) }

    final override fun translateAll(input: Iterable<String>) = translateColors(translateTags(input))

    final override fun translateAll(input: String): String = translateColors(translateTags(input))
    override fun translateItemStack(itemStack: ItemStack): ItemStack {
        val meta = itemStack.itemMeta
        if (meta.hasDisplayName()) {
            meta.displayName = translateAll(meta.displayName)
        }
        if (meta.hasLore()) {
            meta.lore = translateAll(meta.lore)
        }
        itemStack.itemMeta = meta
        return itemStack
    }

    override fun broadcastMessage(vararg messages: String) = messages.forEach { Bukkit.broadcastMessage(translateAll(it)) }

}