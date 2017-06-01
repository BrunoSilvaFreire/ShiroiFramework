package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.misc.variable.MessageVariable
import me.ddevil.shiroi.craft.misc.variable.VariableProvider

class MessageSeparatorVariableProvider(val messageSeparator: String) : VariableProvider {
    companion object {
        const val SEPARATOR_VARIABLE_NAME = "separator"
    }

    override fun provide(): MessageVariable {
        return MessageVariable(SEPARATOR_VARIABLE_NAME, messageSeparator)
    }
}