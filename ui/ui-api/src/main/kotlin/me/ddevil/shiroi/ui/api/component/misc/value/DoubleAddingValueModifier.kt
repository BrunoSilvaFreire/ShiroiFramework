package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack

class DoubleAddingValueModifier : AddingValueModifier<Double> {

    @JvmOverloads
    constructor(
            getter: () -> Double,
            setter: (Double) -> Unit,
            valueToAdd: Double,
            updater: ValueModifierUpdater<Double>,
            initialIcon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, updater, initialIcon, id)

    @JvmOverloads
    constructor(
            getter: () -> Double,
            setter: (Double) -> Unit,
            valueToAdd: Double,
            icon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, icon, id)

    companion object {
        val func = { a: Double, b: Double -> a + b }
    }
}
