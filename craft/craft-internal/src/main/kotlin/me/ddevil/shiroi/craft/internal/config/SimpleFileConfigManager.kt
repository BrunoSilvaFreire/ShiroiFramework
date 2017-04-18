package me.ddevil.shiroi.craft.internal.config

import me.ddevil.shiroi.craft.config.FileConfigKey
import me.ddevil.shiroi.craft.config.FileConfigValue
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration


open class SimpleFileConfigManager<out P : ShiroiPlugin<*, *>, K>
constructor(
        plugin: P,
        configKey: Class<K>, colorDesignValue: FileConfigValue<ConfigurationSection, K>) :
        EnumFileConfigManager<P, K>(configKey, colorDesignValue, plugin) where   K : Enum<*>, K : FileConfigKey {

    override fun enable() {

    }

    override fun disable() {
    }

    private val configFileCache: MutableMap<K, FileConfiguration> = mutableMapOf()

    override fun getConfig(key: K): FileConfiguration {
        return configFileCache.getOrPut(key) { YamlConfiguration.loadConfiguration(getFile(key)) }
    }

    override fun <T> getValue0(value: FileConfigValue<T, K>): T? {
        //todo: fix this
        val config = getConfig(value.key)
        val ovj = config[value.path]
        if (ovj == null) {
            config[value.path] = value.defaultValue
            config.save(getFile(value.key))
            return value.defaultValue
        }
        return ovj as? T ?: throw IllegalStateException("Unexpected value type for config value '${value.path}' @ '${value.key.name}'")
    }

    override fun reload() {
        super.reload()
        configFileCache.clear()
    }

}