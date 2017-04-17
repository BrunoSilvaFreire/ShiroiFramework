package me.ddevil.shiroi.craft.util

import me.ddevil.shiroi.util.exception.IllegalValueTypeException
import me.ddevil.shiroi.util.exception.ValueNotFoundException
import me.ddevil.util.serialization.Serializable
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration
import java.util.*

fun createConfig(serializable: Serializable) = createConfig(serializable.serialize())

fun Map<String, Any>.toConfig() = createConfig(this)

fun createConfig(map: Map<String, Any>): ConfigurationSection {
    val config = MemoryConfiguration()
    config.set(map)
    return config
}

fun ConfigurationSection.set(map: Map<String, Any>) {
    map.forEach {
        this[it.key] = it.value
    }
}

inline fun <reified T : Any> ConfigurationSection.getOrException(key: String): T {
    val get = this[key] ?: throw ValueNotFoundException(key)
    return (get as? T) ?: throw IllegalValueTypeException(T::class.java, get.javaClass)
}

fun ConfigurationSection.toMap(): Map<String, Any> {
    val map = HashMap<String, Any>()
    for ((key, value) in this.getValues(false)) {
        if (value is ConfigurationSection) {
            map.put(key, value.toMap())
        } else {
            map.put(key, value)
        }
    }
    return map
}
