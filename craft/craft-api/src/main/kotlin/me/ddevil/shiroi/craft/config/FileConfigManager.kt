package me.ddevil.shiroi.craft.config

import org.bukkit.configuration.file.FileConfiguration
import java.io.File

interface FileConfigManager<K : FileConfigSource, out C : FileConfiguration> : ConfigManager<K> {

    fun <T : Any> getValue(value: FileConfigValue<T, K>): T

    fun getSource(key: K): C

    fun getFile(key: K): File
}