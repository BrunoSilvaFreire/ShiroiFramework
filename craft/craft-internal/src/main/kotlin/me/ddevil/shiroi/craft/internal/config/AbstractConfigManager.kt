package me.ddevil.shiroi.craft.internal.config

import me.ddevil.shiroi.craft.config.ConfigKey
import me.ddevil.shiroi.craft.config.ConfigManager
import me.ddevil.shiroi.craft.config.ConfigValue
import me.ddevil.shiroi.util.misc.Toggleable

abstract class AbstractConfigManager<K : ConfigKey> : ConfigManager<K> {

    private val cache: MutableMap<ConfigValue<*, K>, Any>

    init {
        cache = mutableMapOf()
    }

    /**
     * todo: Find a better way to deal with the type cast shitstorm
     */
    final override fun <T> getValue(value: ConfigValue<T, K>) = cache.getOrPut(value, { getValue0(value) as Any }) as T?

    abstract fun <T> getValue0(value: ConfigValue<T, K>): T

    override fun <T : Toggleable> T.enable() = this

    override fun <T : Toggleable> T.disable() = this

    override fun reload() {
        cache.clear()
    }
}