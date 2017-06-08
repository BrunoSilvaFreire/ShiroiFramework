package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack

private fun <E : Enum<E>> getEnums(enum: Class<E>) = enum.enumConstants.asIterable()

class EnumValueModifier<E : Enum<E>> : ScrollingValueModifier<E> {
    constructor(enum: Class<E>,
                getter: () -> E,
                setter: (E) -> Unit,
                defaultIcon: ItemStack,
                updater: ValueModifierUpdater<E>,
                id: String?,
                initIndex: Int) : super(getEnums(enum), getter, setter, defaultIcon, updater, id, initIndex)


    constructor(enum: Class<E>,
                icon: ItemStack,
                getter: () -> E,
                setter: (E) -> Unit,
                id: String?,
                initIndex: Int) : super(getEnums(enum), icon, getter, setter, id, initIndex)
}