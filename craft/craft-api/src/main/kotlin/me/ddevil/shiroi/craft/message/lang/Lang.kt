package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.config.ConfigValue

/**
 * Represents a message that can be translated and sent using variables
 */
class Lang<out K : ConfigValue<String, *>>(val key: K) 