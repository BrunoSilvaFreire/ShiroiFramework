package me.ddevil.shiroi.craft.misc.variable


class VariableManager {

    private val providerMap = HashMap<String, VariableProvider>()

    fun registerVariableProvider(variableName: String, provider: VariableProvider) {
        providerMap[variableName] = provider
    }

    fun getVariableProvider(variableName: String): VariableProvider {
        return providerMap[variableName] ?: throw IllegalArgumentException()
    }
}

