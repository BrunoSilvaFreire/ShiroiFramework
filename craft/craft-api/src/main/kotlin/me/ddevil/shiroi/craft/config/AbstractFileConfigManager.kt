package me.ddevil.shiroi.craft.config

import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import java.io.File
import java.util.logging.Level

abstract class AbstractFileConfigManager<out P : ShiroiPlugin<*, *>, K : FileConfigKey>
constructor(val plugin: P) : FileConfigManager<K> {

    private val cache: MutableMap<FileConfigValue<*, K>, Any> = mutableMapOf()

    override fun <T> getValue(value: FileConfigValue<T, K>): T {
        /**
         * todo: Find a better way to deal with the type cast shitstorm
         */
        if (cache.contains(value)) {
            return cache[value]!! as? T ?: throw IllegalStateException("Wrong type found for config value '${value.path}' @ ${value.key.name}")
        }
        var loaded = getValue0(value)
        if (loaded == null) {
            plugin.logger.log(Level.WARNING, "Couldn't find value for '${value.path}', loading default one...")
            loaded = value.defaultValue
            val config = getConfig(value.key)
            config.set(value.path, loaded)
            config.save(getFile(value.key))
        }
        cache.put(value, loaded as Any)

        return loaded
    }

    fun getFile(key: K): File {
        val file = File(plugin.dataFolder, key.folderPath)
        if (!file.exists()) {
            plugin.saveResource(key.resourcePath, file)
        }
        return file
    }


    abstract fun <T> getValue0(value: FileConfigValue<T, K>): T?
    override fun reload() {
        cache.clear()
    }
}