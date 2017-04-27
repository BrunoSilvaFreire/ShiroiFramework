package me.ddevil.shiroi.example

import me.ddevil.shiroi.craft.config.FileConfigManager
import me.ddevil.shiroi.craft.config.SimpleFileConfigManager
import me.ddevil.shiroi.craft.message.SimpleMessageManager
import me.ddevil.shiroi.craft.message.TagTranslator
import me.ddevil.shiroi.craft.plugin.AbstractPlugin
import me.ddevil.shiroi.craft.plugin.PluginSettings
import me.ddevil.shiroi.example.config.GenericConfigKey
import me.ddevil.shiroi.example.config.GenericConfigValue
import me.ddevil.shiroi.example.ui.GenericUIConstants

@PluginSettings(primaryAcronym = "gp", aliases = arrayOf("gps"))
class GenericPlugin : AbstractPlugin<SimpleMessageManager<GenericPlugin>, FileConfigManager<GenericConfigKey>>() {
    override fun loadConfigManager(): FileConfigManager<GenericConfigKey> {
        return SimpleFileConfigManager(this,
                GenericConfigKey::class.java,
                GenericConfigValue.COLOR_DESIGN
        )
    }

    override fun onEnable0() {
        pluginLogger.log("Loading UI constants...")
        GenericUIConstants.setup(this)
        commandManager.registerCommand(GenericCommand(this))
    }

    override fun loadMessageManager(): SimpleMessageManager<GenericPlugin> {
        return SimpleMessageManager.create(
                this,
                configManager,
                GenericConfigValue.MESSAGE_SEPARATOR,
                GenericConfigValue.PLUGIN_PREFIX,
                listOf(
                        TagTranslator("date") { System.currentTimeMillis().toString() }
                ))
    }

}