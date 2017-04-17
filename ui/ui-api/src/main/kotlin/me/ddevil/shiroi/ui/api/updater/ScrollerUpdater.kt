package me.ddevil.shiroi.ui.api.updater

import me.ddevil.shiroi.ui.api.component.scrollable.Scrollable
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import org.bukkit.inventory.ItemStack

/**
 * Created by bruno on 05/10/2016.
 */
interface ScrollerUpdater<in O : Scrollable<*>> : Updater {
    fun update(scrollable: O, direction: ScrollDirection): ItemStack
}
