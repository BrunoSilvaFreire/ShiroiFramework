package me.ddevil.shiroi.ui.api.component.misc

import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import org.bukkit.inventory.ItemStack


open class ClickableItemSlotComponent : Clickable, ItemSlotComponent {
    final override val action: Action

    @JvmOverloads
    constructor(updater: ItemUpdater,
                defaultIcon: ItemStack,
                action: Action,
                id: String? = null) : super(updater,
            defaultIcon,
            id) {
        this.action = action
    }

    constructor(icon: ItemStack, id: String?, action: Action) : super(icon, id) {
        this.action = action
    }

}