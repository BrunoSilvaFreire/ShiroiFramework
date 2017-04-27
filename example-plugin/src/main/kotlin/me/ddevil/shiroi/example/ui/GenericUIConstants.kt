package me.ddevil.shiroi.example.ui

import me.ddevil.shiroi.craft.util.ShiroiItemBuilder
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
        PRIMARY_BACKGROUND = ShiroiItemBuilder(plugin.messageManager, Material.IRON_FENCE)
                .setName("&r")
                .build()
        SECONDARY_BACKGROUND = ShiroiItemBuilder(plugin.messageManager,
                ItemStack(Material.STAINED_GLASS_PANE, 1, 15))
                .setName("&r")
                .build()
        this.MENU = GenericMenu(plugin)
        MENU.register()
    }
}