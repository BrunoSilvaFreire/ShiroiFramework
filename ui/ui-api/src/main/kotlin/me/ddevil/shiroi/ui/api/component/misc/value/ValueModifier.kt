package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.component.Clickable
import org.bukkit.inventory.ItemStack

interface ValueModifier<T> : Clickable {
    val value: T
    fun updateIcon(updater: ValueModifierUpdater<T>, oldItem: ItemStack): ItemStack
}