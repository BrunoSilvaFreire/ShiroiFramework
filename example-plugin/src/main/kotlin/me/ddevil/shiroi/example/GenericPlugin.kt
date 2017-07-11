package me.ddevil.shiroi.example

import me.ddevil.shiroi.craft.config.YAMLFileConfigManager
import me.ddevil.shiroi.craft.message.SimpleMessageManager
import me.ddevil.shiroi.craft.misc.variable.MessageVariable
import me.ddevil.shiroi.craft.misc.variable.SingleVariableProvider
import me.ddevil.shiroi.craft.misc.variable.VariableProvider
import me.ddevil.shiroi.craft.plugin.AbstractPlugin
import me.ddevil.shiroi.craft.plugin.PluginSettings
import me.ddevil.shiroi.example.config.GenericConfigSource
import me.ddevil.shiroi.example.config.GenericConfigValue
import me.ddevil.shiroi.example.ui.GenericUIConstants

@PluginSettings(primaryAcronym = "gp", aliases = arrayOf("gps"))
class GenericPlugin : AbstractPlugin<SimpleMessageManager, YAMLFileConfigManager<GenericConfigSource>>() {
    override fun loadConfigManager(): YAMLFileConfigManager<GenericConfigSource> {
        return YAMLFileConfigManager(this, GenericConfigValue.COLOR_DESIGN)
    }

    override fun onEnable0() {
        pluginLogger.log("Loading UI constants...")
        GenericUIConstants.setup(this)
        commandManager.registerCommand(GenericCommand(this))
    }

    override fun loadMessageManager() = SimpleMessageManager.create(
            this,
            configManager,
            GenericConfigValue.MESSAGE_SEPARATOR,
            GenericConfigValue.PLUGIN_PREFIX,
            listOf(
                    DateVariableProvider()
            )
    )

}

class DateVariableProvider : SingleVariableProvider() {
    override val variable= MessageVariable("date", System.currentTimeMillis().toString())

}
