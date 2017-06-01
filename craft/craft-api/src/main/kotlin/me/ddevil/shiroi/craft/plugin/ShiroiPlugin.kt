package me.ddevil.shiroi.craft.plugin

import me.ddevil.shiroi.craft.command.CommandManager
import me.ddevil.shiroi.craft.config.ConfigManager
import me.ddevil.shiroi.craft.log.PluginLogger
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.misc.CraftReloadable
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.misc.master.MasterConfig
import me.ddevil.shiroi.craft.misc.task.ChainFactory
import me.ddevil.shiroi.craft.misc.variable.VariableManager
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import java.io.File

interface ShiroiPlugin<out M : MessageManager, out C : ConfigManager<*>> : Plugin, CraftReloadable {

    val settings: PluginSettings
    val masterConfig: MasterConfig
    val messageManager: M
    val configManager: C
    val commandManager: CommandManager
    var colorDesign: PluginColorDesign
    val pluginLogger: PluginLogger
    val chainFactory: ChainFactory
    val allKnownAliases: Array<String>
    val variableManager: VariableManager
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
    fun saveResource(resourcePath: String, destination: File)
}