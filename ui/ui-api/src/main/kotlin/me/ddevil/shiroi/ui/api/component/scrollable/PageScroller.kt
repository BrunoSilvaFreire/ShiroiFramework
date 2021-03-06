package me.ddevil.shiroi.ui.api.component.scrollable

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.container.Menu
import me.ddevil.shiroi.ui.api.component.misc.AbstractSlotComponent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import me.ddevil.shiroi.ui.api.updater.ScrollerUpdater
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class PageScroller<O : Scrollable<*>>
@JvmOverloads
constructor(
        parentMenu: Menu<*>,
        var parent: O,
        private val direction: ScrollDirection,
        updater: ScrollerUpdater<O>,
        id: String? = null) : AbstractSlotComponent<ScrollerUpdater<O>>(
        updater,
        ItemStack(Material.EMERALD),
        id), Clickable {

    override val action: Action

    override fun updateIcon(updater: ScrollerUpdater<O>,
                            oldItem: ItemStack): ItemStack {
        return updater.update(oldItem, parent, direction)
    }

    init {
        this.action = object : Action {
            override fun invoke(e: UIClickEvent, localPosition: UIPosition) {
                parent.scroll(direction)
                parentMenu.drawChild(parent)
            }
        }
    }
}