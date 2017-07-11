package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.misc.variable.MessageVariable
import me.ddevil.shiroi.craft.misc.variable.SingleVariableProvider
import me.ddevil.shiroi.craft.misc.variable.VariableProvider

class MessageSeparatorVariableProvider(val messageSeparator: String) : SingleVariableProvider() {
    companion object {
        const val SEPARATOR_VARIABLE_NAME = "separator"
    }

    override val variable = MessageVariable(SEPARATOR_VARIABLE_NAME, messageSeparator)
}