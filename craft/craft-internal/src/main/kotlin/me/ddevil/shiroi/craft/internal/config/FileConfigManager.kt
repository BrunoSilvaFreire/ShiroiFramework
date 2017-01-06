package me.ddevil.shiroi.craft.internal.config

import me.ddevil.shiroi.craft.config.ConfigKey
import me.ddevil.shiroi.craft.config.ConfigValue
import me.ddevil.shiroi.craft.internal.config.misc.FileConfigKey
import org.bukkit.configuration.ConfigurationSection


class FileConfigManager<K>(configKey: Class<K>, colorDesignValue: ConfigValue<ConfigurationSection, K>) :
        EnumConfigManager<K>(configKey, colorDesignValue) where   K : Enum<*>, K : FileConfigKey {

    override fun <T> getValue0(value: ConfigValue<T, K>): T {
        if (value is FileConfigKey) {

            //todo: fix this
            throw IllegalArgumentException("Random exception just so it compiles and I can get some sleep")
        } else {
            throw IllegalArgumentException("Invalid value type!")
        }
    }

    private fun getFile(key: FileConfigKey) {

    }


}