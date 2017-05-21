package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.Bukkit

abstract class AbstractMessageManager(val plugin: ShiroiPlugin<*, *>) : MessageManager {

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

    override fun broadcastMessage(vararg messages: String) = messages.forEach { Bukkit.broadcastMessage(translateAll(it)) }

}