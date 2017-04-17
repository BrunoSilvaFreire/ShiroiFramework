package me.ddevil.shiroi.craft.config

interface ConfigValue<out T, out K : ConfigKey> {
    val key: K
    val defaultValue: T
}