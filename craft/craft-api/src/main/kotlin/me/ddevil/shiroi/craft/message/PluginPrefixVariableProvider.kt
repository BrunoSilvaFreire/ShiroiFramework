package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.misc.variable.MessageVariable
import me.ddevil.shiroi.craft.misc.variable.VariableProvider


class PluginPrefixVariableProvider(val prefix: String) : VariableProvider {
    companion object {
        const val PREFIX_VARIABLE_NAME = "prefix"
    }

    override fun provide(): MessageVariable {
        return MessageVariable(PREFIX_VARIABLE_NAME, prefix)
    }

}