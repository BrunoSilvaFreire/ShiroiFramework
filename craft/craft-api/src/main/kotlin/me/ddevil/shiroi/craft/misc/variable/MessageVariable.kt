package me.ddevil.shiroi.craft.misc.variable

class MessageVariable(val name: String, val value: String) {
    val replacer get() = "{$name}"

    fun replace(string: String) = string.replace(replacer, value)
}