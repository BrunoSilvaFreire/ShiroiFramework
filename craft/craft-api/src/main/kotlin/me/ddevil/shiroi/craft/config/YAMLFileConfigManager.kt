package me.ddevil.shiroi.craft.config

import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File


open class YAMLFileConfigManager<K : FileConfigSource>
constructor(
        plugin: ShiroiPlugin<*, *>,
        colorDesignKey: FileConfigValue<*, K>
) : AbstractFileConfigManager<K, YamlConfiguration>(plugin, colorDesignKey) {

    override fun loadConfig(file: File): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(file)
    }

    override fun <T : Any> loadValue(value: FileConfigValue<T, K>, source: YamlConfiguration): Any? {
        return source[value.path]
    }

}