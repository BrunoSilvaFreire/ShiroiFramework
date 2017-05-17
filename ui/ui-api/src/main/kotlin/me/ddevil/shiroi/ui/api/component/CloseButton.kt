package me.ddevil.shiroi.ui.api.component

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.misc.ClickableItemSlotComponent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class CloseButton : ClickableItemSlotComponent {

    @JvmOverloads
    constructor(updater: ItemUpdater, id: String? = null) : super(updater,
            ItemStack(Material.BARRIER),
            id,
            CloseAction())

    @JvmOverloads
    constructor(icon: ItemStack, id: String? = null) : super(icon, id, CloseAction())
}

class CloseAction : Action {
    override fun invoke(e: UIClickEvent, localPosition: UIPosition) = e.player.closeInventory()
}
