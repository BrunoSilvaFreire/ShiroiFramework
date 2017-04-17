package me.ddevil.shiroi.ui.api.updater

import org.bukkit.inventory.ItemStack

interface ItemUpdater : Updater {
    fun update(oldItem: ItemStack): ItemStack
}
