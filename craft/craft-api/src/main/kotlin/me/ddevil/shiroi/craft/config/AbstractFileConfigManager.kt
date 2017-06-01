package me.ddevil.shiroi.craft.config

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.shiroi.craft.util.toMap
import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import java.io.File

/**
 * An abstract implementation of a [FileConfigManager] independent of file format, implementations of this class
 * must solely handle getting formats from it's
 */
abstract class AbstractFileConfigManager<K : FileConfigSource, S : FileConfiguration>
constructor(
        val plugin: ShiroiPlugin<*, *>,
        val colorDesignKey: FileConfigValue<*, K>
) : FileConfigManager<K, S> {

    private val loadedConfigFiles: MutableMap<K, S> = mutableMapOf()
    private val loadedValues: MutableMap<FileConfigValue<*, K>, Any> = mutableMapOf()

    override val availableConfigs: Set<K>
        get() = HashSet(loadedConfigFiles.keys)


    abstract fun <T : Any> loadValue(value: FileConfigValue<T, K>, source: S): Any?

    abstract fun loadConfig(file: File): S

    override fun enable() {}

    override fun disable() {}

    override fun getSource(key: K): S {
        return loadedConfigFiles.getOrPut(key) { loadConfig(getFile(key)) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getValue(value: FileConfigValue<T, K>): T {
        if (value in loadedValues) {
            //Value was already loaded
            return loadedValues[value] as T
        }

        val source = getSource(value.source)
        val loadedValue = loadValue(value, source)

        //Type-safe variable
        val finalValue: T

        if (loadedValue == null) {
            //Config doesn't have a value, load default
            plugin.pluginLogger.log("Couldn't find value for '${value.path}', loading default one...")
            finalValue = value.defaultValue
            val config = getSource(value.source)
            config.set(value.path, finalValue)
            config.save(getFile(value.source))
        } else {
            //Found value, try to get it as the right type
            try {
                finalValue = (loadedValue as T)
            } catch(e: Exception) {
                //Value has wrong type
                val msg = "Unexpected value type for config value '${value.path}' @ '${value.source.name}'"
                throw IllegalStateException(msg, e)
            }
        }
        loadedValues.put(value, finalValue)
        return finalValue
    }

    override fun getFile(key: K): File {
        val file = File(plugin.dataFolder, key.folderPath)
        if (!file.exists()) {
            plugin.saveResource(key.resourcePath, file)
        }
        return file
    }

    override fun reload(sender: CommandSender) {
        val totalFiles = loadedConfigFiles.size
        loadedConfigFiles.clear()
        plugin.messageManager.sendMessage(sender, "Unloaded $1$totalFiles $3config files.")
        val totalValues = loadedValues.size
        loadedValues.clear()
        plugin.messageManager.sendMessage(sender, "Unloaded $1$totalValues $3cached values.")
    }

    override fun reload() {
        loadedConfigFiles.clear()
        loadedValues.clear()
    }

    override fun loadColorDesign(): PluginColorDesign? {
        val foundValue = getValue(colorDesignKey)
        val map: Map<String, Any>
        if (foundValue is ConfigurationSection) {
            map = foundValue.toMap()
        } else if (foundValue is Map<*, *>) {
            map = foundValue as Map<String, Any>
        } else {
            throw IllegalArgumentException("Expected Map or ConfigSection to load PluginColorDesign! Got $foundValue")
        }
        return PluginColorDesign(map)
    }
}