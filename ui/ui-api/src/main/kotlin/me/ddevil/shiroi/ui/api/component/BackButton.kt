package me.ddevil.shiroi.ui.api.component

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.container.Menu
import me.ddevil.shiroi.ui.api.component.misc.ClickableItemSlotComponent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class BackButton : ClickableItemSlotComponent {

    val returningMenu: Menu<*>

    @JvmOverloads
    constructor(updater: ItemUpdater, returningMenu: Menu<*>, id: String? = null) : super(updater,
            ItemStack(Material.EMERALD),
            BackAction(returningMenu),
            id) {
        this.returningMenu = returningMenu
    }

    @JvmOverloads
    constructor(icon: ItemStack, returningMenu: Menu<*>, id: String? = null) : super(icon,
            id,
            BackAction(returningMenu)) {
        this.returningMenu = returningMenu
    }
}

class BackAction(private val returningMenu: Menu<*>) : Action {

    override fun invoke(e: UIClickEvent, localPosition: UIPosition) {
        returningMenu.open(e.player)
    }
}