package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.config.ConfigKey
import me.ddevil.shiroi.craft.config.ConfigValue

class Lang<out K : ConfigValue<String, V>, out V : ConfigKey>(val key: K) {

    fun translate(original: String, vararg variables: MessageVariable): String {
        var modified = original
        for (variable in variables) {
            modified = modified.replace(variable.replacer, variable.value)
        }
        return modified
    }

}