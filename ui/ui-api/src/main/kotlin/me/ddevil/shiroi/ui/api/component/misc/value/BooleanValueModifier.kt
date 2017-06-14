package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack

private val BOOLEAN_VALUES = setOf(true, false)

class BooleanValueModifier : ScrollingValueModifier<Boolean> {
    constructor(getter: () -> Boolean,
                setter: (Boolean) -> Unit,
                defaultIcon: ItemStack,
                updater: ValueModifierUpdater<Boolean>,
                initIndex: Int = 0,
                id: String? = null) : super(BOOLEAN_VALUES, getter, setter, defaultIcon, updater, id, initIndex)

    constructor(getter: () -> Boolean,
                setter: (Boolean) -> Unit,
                icon: ItemStack,
                initIndex: Int = 0,
                id: String? = null) : super(BOOLEAN_VALUES, icon, getter, setter, id, initIndex)
}