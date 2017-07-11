package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.misc.variable.MessageVariable
import me.ddevil.shiroi.craft.misc.variable.SingleVariableProvider
import me.ddevil.shiroi.craft.misc.variable.VariableProvider


class PluginPrefixVariableProvider(prefix: String) : SingleVariableProvider() {
    override val variable = MessageVariable(PREFIX_VARIABLE_NAME, prefix)

    companion object {
        const val PREFIX_VARIABLE_NAME = "prefix"
    }

}