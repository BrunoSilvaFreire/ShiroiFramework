package me.ddevil.shiroi.ui.shiroi

import me.ddevil.shiroi.craft.event.ShiroiPluginReloadEvent
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.shiroi.ui.api.component.container.MenuSize
import me.ddevil.shiroi.ui.api.component.menu.IndependentMenu
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.inventory.ItemStack

abstract class ShiroiMenu<out P : ShiroiPlugin<*, *>>
@JvmOverloads
constructor(
        plugin: P,
        title: String? = null,
        size: MenuSize = MenuSize.SIX_ROWS,
        background: ItemStack? = null,
        id: String? = null
) : IndependentMenu<P>(plugin, parseTitle(title, plugin.messageManager), size, background, id) {
    private val initialTitle: String? = title

    companion object {
        private fun parseTitle(title: String?, messageManager: MessageManager): String? {
            return if (title != null) {
                messageManager.translateAll(title)
            } else {
                null
            }
        }
    }

    @EventHandler
    fun onReload(e: ShiroiPluginReloadEvent) {
        if (initialTitle != null) {
            this.inventory = Bukkit.createInventory(null, size, plugin.messageManager.translateAll(initialTitle))
        }
    }
}