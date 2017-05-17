package me.ddevil.shiroi.ui.api.component.menu

import me.ddevil.shiroi.ui.api.MAX_INVENTORY_SIZE_X
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.container.MenuSize
import me.ddevil.shiroi.ui.api.event.InteractionType
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

abstract class IndependentMenu<out P : Plugin>
@JvmOverloads
constructor(
        plugin: P,
        title: String?,
        size: MenuSize = MenuSize.SIX_ROWS,
        background: ItemStack? = null,
        id: String? = null
) : ContainerMenu<P>(plugin, title, size, background, id), Listener {


    fun register() {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        val clickedInventory = e.inventory
        if ((clickedInventory != null) && clickedInventory == this.inventory) {
            val rawSlot = e.rawSlot
            if (rawSlot < 0) {
                return
            }
            if (rawSlot < clickedInventory.size) {
                e.isCancelled = true
                val slot = e.slot
                val position = UIPosition.fromSlot(slot, MAX_INVENTORY_SIZE_X)
                if (hasObjectIn(position)) {
                    val drawable = get(position)
                    if (drawable is Clickable) {
                        val type = getType(e)
                        val call = UIClickEvent(
                                drawable,
                                position,
                                e.whoClicked as Player,
                                type,
                                null
                        ).call()
                        action.invoke(call, position)
                    }
                }
            }
        }
    }

    open fun getType(e: InventoryClickEvent): InteractionType {
        when {
            e.isLeftClick -> return InteractionType.INVENTORY_CLICK_LEFT
            else -> return InteractionType.INVENTORY_CLICK_RIGHT
        }
    }
}