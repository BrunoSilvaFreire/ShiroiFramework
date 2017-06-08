package me.ddevil.shiroi.ui.api.component.misc

import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import org.bukkit.inventory.ItemStack

open class ItemSlotComponent : AbstractSlotComponent<ItemUpdater> {
    constructor(updater: ItemUpdater, defaultIcon: ItemStack, id: String?) : super(updater, defaultIcon, id)
    constructor(icon: ItemStack, id: String?) : super(icon, id)

    override fun updateIcon(updater: ItemUpdater,
                            oldItem: ItemStack) = updater.update(oldItem)
}
