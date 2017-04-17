package me.ddevil.shiroi.ui.internal.component.menu

import me.ddevil.shiroi.ui.api.component.container.MenuSize
import me.ddevil.shiroi.ui.api.event.InteractionType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

abstract class PlayerPortableMenu<out P : Plugin>
@JvmOverloads
constructor(
        plugin: P,
        title: String?,
        size: MenuSize = MenuSize.SIX_ROWS,
        background: ItemStack? = null,
        id: String? = null
) : IndependentMenu<P>(plugin, title, size, background, id) {

    override fun getType(e: InventoryClickEvent): InteractionType {
        when {
            e.isLeftClick -> return InteractionType.INTERACT_CLICK_LEFT
            else -> return InteractionType.INTERACT_CLICK_RIGHT
        }
    }

}