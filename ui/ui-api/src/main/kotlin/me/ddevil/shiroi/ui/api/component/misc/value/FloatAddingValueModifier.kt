package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack

class FloatAddingValueModifier : AddingValueModifier<Float> {

    @JvmOverloads
    constructor(
            getter: () -> Float,
            setter: (Float) -> Unit,
            valueToAdd: Float,
            updater: ValueModifierUpdater<Float>,
            initialIcon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, updater, initialIcon, id)

    @JvmOverloads
    constructor(
            getter: () -> Float,
            setter: (Float) -> Unit,
            valueToAdd: Float,
            icon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, icon, id)

    companion object {
        val func = { a: Float, b: Float -> a + b }
    }
}