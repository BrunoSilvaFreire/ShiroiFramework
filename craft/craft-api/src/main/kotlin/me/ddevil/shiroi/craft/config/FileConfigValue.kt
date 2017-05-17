package me.ddevil.shiroi.craft.config

interface FileConfigValue<out T : Any, out K : ConfigSource> : ConfigValue<T, K> {
    val path: String
}