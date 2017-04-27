package me.ddevil.shiroi.craft.config

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.shiroi.craft.util.toMap
import org.bukkit.configuration.ConfigurationSection

abstract class EnumFileConfigManager<out P : ShiroiPlugin<*, *>, K>(configKey: Class<K>,
                                                                    val colorDesignValue: FileConfigValue<ConfigurationSection, K>,
                                                                    plugin: P) :
        AbstractFileConfigManager<P, K>(plugin)where K : Enum<*>, K : FileConfigKey {

    final override val availableConfigs: Set<K> = configKey.enumConstants.toSet()

    override fun loadColorDesign(): PluginColorDesign {
        return PluginColorDesign(getValue(colorDesignValue).toMap())
    }

}