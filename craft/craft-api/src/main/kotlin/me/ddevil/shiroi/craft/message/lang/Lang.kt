package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.config.ConfigValue
import me.ddevil.shiroi.craft.misc.variable.MessageVariable

/**
 * Represents a message that can be translated and sent using variables
 */
class Lang<out K : ConfigValue<String, *>>(val key: K) {

    fun translate(original: String, vararg variables: MessageVariable): String {
        var modified = original
        for (variable in variables) {
            modified = modified.replace(variable.replacer, variable.value)
        }
        return modified
    }

}