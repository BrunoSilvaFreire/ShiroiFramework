package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack

class IntAddingValueModifier : AddingValueModifier<Int> {

    @JvmOverloads
    constructor(
            getter: () -> Int,
            setter: (Int) -> Unit,
            valueToAdd: Int,
            updater: ValueModifierUpdater<Int>,
            initialIcon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, updater, initialIcon, id)

    @JvmOverloads
    constructor(
            getter: () -> Int,
            setter: (Int) -> Unit,
            valueToAdd: Int,
            icon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, icon, id)

    companion object {
        val func = { a: Int, b: Int -> a + b }
    }
}