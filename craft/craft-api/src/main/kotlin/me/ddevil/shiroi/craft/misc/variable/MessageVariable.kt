package me.ddevil.shiroi.craft.misc.variable

fun translateVariables(string: String, vararg variables: MessageVariable): String {
    var modified = string
    for (variable in variables) {
        modified = variable.replace(modified)
    }
    return modified
}

class MessageVariable(val name: String, val value: String) {
    val replacer get() = "{$name}"

    fun replace(string: String) = string.replace(replacer, value)
}