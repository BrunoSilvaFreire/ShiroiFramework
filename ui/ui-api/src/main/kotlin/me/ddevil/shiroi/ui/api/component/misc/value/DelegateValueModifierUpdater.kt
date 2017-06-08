package me.ddevil.shiroi.ui.api.component.misc.value

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class DelegateValueModifierUpdater<in T>(
        val metaUpdater: (ItemMeta) -> (Unit),
        val materialUpdater: (ItemStack) -> (Unit)
) : ValueModifierUpdater<T> {
    override fun update(oldItem: ItemStack, selectedValue: T): ItemStack {
        val meta = oldItem.itemMeta
        metaUpdater(meta)
        oldItem.itemMeta = meta
        materialUpdater(oldItem)
        return oldItem
    }

}