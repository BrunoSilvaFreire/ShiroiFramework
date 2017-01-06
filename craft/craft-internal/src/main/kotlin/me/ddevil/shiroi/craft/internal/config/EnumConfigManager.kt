package me.ddevil.shiroi.craft.internal.config

import me.ddevil.shiroi.craft.config.ConfigKey
import me.ddevil.shiroi.craft.config.ConfigValue
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.util.ConfigUtil.toMap
import org.bukkit.configuration.ConfigurationSection

abstract class EnumConfigManager<K>(configKey: Class<K>, val colorDesignValue: ConfigValue<ConfigurationSection, K>) :
        AbstractConfigManager<K>()where K : Enum<*>, K : ConfigKey {

    override val availableConfigs: Set<K>

    init {
        this.availableConfigs = configKey.enumConstants.toSet()
    }

    override fun loadColorDesign(): PluginColorDesign? {
        val value = getValue(colorDesignValue)
        when {
            value != null -> return PluginColorDesign(value.toMap())
            else -> return null
        }
    }

}