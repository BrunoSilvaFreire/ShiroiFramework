package me.ddevil.shiroi.craft.plugin

import me.ddevil.shiroi.craft.command.CommandManager
import me.ddevil.shiroi.craft.config.ConfigManager
import me.ddevil.shiroi.craft.log.PluginLogger
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.misc.PluginSettings
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.misc.task.ChainFactory
import me.ddevil.shiroi.util.misc.Reloadable
import org.bukkit.plugin.Plugin

interface ShiroiPlugin<out M : MessageManager, out C : ConfigManager<*>> : Plugin, Reloadable {

    val settings: PluginSettings
    val messageManager: M
    val configManager: C
    val commandManager: CommandManager
    var colorDesign: PluginColorDesign
    val pluginLogger: PluginLogger
    val chainFactory: ChainFactory
}