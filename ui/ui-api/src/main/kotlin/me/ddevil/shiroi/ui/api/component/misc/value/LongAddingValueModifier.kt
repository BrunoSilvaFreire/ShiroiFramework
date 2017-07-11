package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack

class LongAddingValueModifier : AddingValueModifier<Long> {

    @JvmOverloads
    constructor(
            getter: () -> Long,
            setter: (Long) -> Unit,
            valueToAdd: Long,
            updater: ValueModifierUpdater<Long>,
            initialIcon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, updater, initialIcon, id)

    @JvmOverloads
    constructor(
            getter: () -> Long,
            setter: (Long) -> Unit,
            valueToAdd: Long,
            icon: ItemStack,
            id: String? = null
    ) : super(getter, setter, func, valueToAdd, icon, id)

    companion object {
        val func = { a: Long, b: Long -> a + b }
    }
}