package me.ddevil.shiroi.craft.config

import me.ddevil.json.JsonObject
import me.ddevil.shiroi.craft.config.misc.JsonConfiguration
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import java.io.File

class JsonFileConfigManager<K : FileConfigSource>
constructor(
        plugin: ShiroiPlugin<*, *>,
        colorDesignKey: FileConfigValue<Map<String, Any>, K>
) : AbstractFileConfigManager<K, JsonConfiguration>(plugin, colorDesignKey) {

    override fun <T : Any> loadValue(value: FileConfigValue<T, K>, source: JsonConfiguration): Any? {
        val path = value.path.split('.')
        var currentMap: JsonObject = source.host ?: return null
        for ((index, section) in path.withIndex()) {
            if (index == path.lastIndex) {
                return currentMap[section]
            }
            currentMap = currentMap.getJson(section)
        }
        return null
    }


    override fun loadConfig(file: File) = JsonConfiguration(file)

}