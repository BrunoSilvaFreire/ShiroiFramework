package me.ddevil.shiroi.example.ui

import me.ddevil.shiroi.craft.util.ItemBuilder
import me.ddevil.shiroi.example.GenericPlugin
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.properties.Delegates

object GenericUIConstants {
    var PRIMARY_BACKGROUND: ItemStack by Delegates.notNull<ItemStack>()
        private set
    var SECONDARY_BACKGROUND: ItemStack by Delegates.notNull<ItemStack>()
        private set
    var MENU: GenericMenu by Delegates.notNull<GenericMenu>()

    fun setup(plugin: GenericPlugin) {
        PRIMARY_BACKGROUND = ItemBuilder(Material.IRON_FENCE).toItemStack()
        SECONDARY_BACKGROUND = ItemBuilder(ItemStack(Material.STAINED_GLASS_PANE, 1, 15),
                plugin.messageManager).setName("&r").toItemStack()
        this.MENU = GenericMenu(plugin)
        MENU.register()
    }
}