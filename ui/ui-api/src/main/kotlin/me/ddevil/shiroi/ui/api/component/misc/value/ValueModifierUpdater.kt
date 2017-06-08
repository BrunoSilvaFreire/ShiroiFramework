package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.updater.Updater
import org.bukkit.inventory.ItemStack

interface ValueModifierUpdater<in T> : Updater {
    fun update(oldItem: ItemStack, selectedValue: T): ItemStack
}