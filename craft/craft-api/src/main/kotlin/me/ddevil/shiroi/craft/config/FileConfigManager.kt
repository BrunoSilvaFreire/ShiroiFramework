package me.ddevil.shiroi.craft.config

import org.bukkit.configuration.file.FileConfiguration

interface FileConfigManager<K : FileConfigKey> : ConfigManager<K> {

    fun <T> getValue(value: FileConfigValue<T, K>): T
    fun getConfig(key: K): FileConfiguration
}