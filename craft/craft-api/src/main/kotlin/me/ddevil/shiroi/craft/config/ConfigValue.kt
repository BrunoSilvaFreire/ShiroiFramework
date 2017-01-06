package me.ddevil.shiroi.craft.config

interface ConfigValue<T, out K: ConfigKey> {
    val key: K
}