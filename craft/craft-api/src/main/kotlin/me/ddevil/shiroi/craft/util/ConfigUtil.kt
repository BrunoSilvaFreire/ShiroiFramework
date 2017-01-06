package me.ddevil.shiroi.craft.util

import org.bukkit.configuration.ConfigurationSection
import java.util.*

object ConfigUtil {
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

}