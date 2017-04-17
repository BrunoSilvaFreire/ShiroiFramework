package me.ddevil.shiroi.craft.config

interface FileConfigValue<out T, out K : ConfigKey> : ConfigValue<T, K> {
    val path: String
}