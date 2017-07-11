package me.ddevil.shiroi.craft.misc.variable

interface VariableProvider {
    fun provide(): Array<MessageVariable>
}
abstract class SingleVariableProvider : VariableProvider{
    abstract val variable: MessageVariable

    override fun provide(): Array<MessageVariable> {
        return arrayOf(variable)
    }
}