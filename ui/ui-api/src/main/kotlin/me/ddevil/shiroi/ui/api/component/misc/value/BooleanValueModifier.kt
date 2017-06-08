package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack

private val BOOLEAN_VALUES = setOf(true, false)

class BooleanValueModifier : ScrollingValueModifier<Boolean> {
    constructor(getter: () -> Boolean,
                setter: (Boolean) -> Unit,
                defaultIcon: ItemStack,
                updater: ValueModifierUpdater<Boolean>,
                id: String?,
                initIndex: Int) : super(BOOLEAN_VALUES, getter, setter, defaultIcon, updater, id, initIndex)

    constructor(icon: ItemStack,
                getter: () -> Boolean,
                setter: (Boolean) -> Unit,
                id: String?,
                initIndex: Int) : super(BOOLEAN_VALUES, icon, getter, setter, id, initIndex)
}