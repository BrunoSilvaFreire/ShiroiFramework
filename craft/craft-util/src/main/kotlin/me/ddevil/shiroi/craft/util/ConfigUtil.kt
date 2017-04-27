package me.ddevil.shiroi.craft.util

import me.ddevil.util.Serializable
import me.ddevil.util.exception.IllegalValueTypeException
import me.ddevil.util.exception.ValueNotFoundException
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

fun ConfigurationSection.getShiroiString(key: String): String = getOrException(key)

fun ConfigurationSection.getShiroiNumber(key: String): Number = getOrException(key)

fun ConfigurationSection.getShiroiFloat(key: String) = getShiroiNumber(key).toFloat()

fun ConfigurationSection.getShiroiDouble(key: String) = getShiroiNumber(key).toDouble()

fun ConfigurationSection.getShiroiInt(key: String) = getShiroiNumber(key).toInt()

fun ConfigurationSection.getShiroiLong(key: String) = getShiroiNumber(key).toLong()

fun <V> ConfigurationSection.getMap(key: String): Map<String, V> = getOrException(key)

fun ConfigurationSection.getShiroiMapAny(key: String): Map<String, Any> = getMap(key)

fun ConfigurationSection.getShiroiBoolean(key: String): Boolean = getOrException(key)

fun <T> ConfigurationSection.getShiroiList(key: String): List<T> = getOrException(key)

inline fun <reified T : Any> ConfigurationSection.getOrException(key: String): T {
    val get = this[key] ?: throw ValueNotFoundException(key)
    return (get as? T) ?: throw IllegalValueTypeException(T::class.java, get.javaClass)
}

inline fun <reified T : Any> ConfigurationSection.getOrElse(key: String, default: T): T {
    val get = this[key] ?: default
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
