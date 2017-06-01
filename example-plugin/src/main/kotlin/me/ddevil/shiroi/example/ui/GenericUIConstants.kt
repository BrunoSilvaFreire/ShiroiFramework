package me.ddevil.shiroi.example.ui

import me.ddevil.shiroi.craft.util.ShiroiItemBuilder
import me.ddevil.shiroi.example.GenericPlugin
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object GenericUIConstants {
    lateinit var PRIMARY_BACKGROUND: ItemStack
        private set
    lateinit var SECONDARY_BACKGROUND: ItemStack
        private set
    lateinit var MENU: GenericMenu
        private set

    fun setup(plugin: GenericPlugin) {
        PRIMARY_BACKGROUND = ShiroiItemBuilder(plugin.messageManager, Material.IRON_FENCE)
                .setName("&r")
                .build()
        SECONDARY_BACKGROUND = ShiroiItemBuilder(plugin.messageManager,
                ItemStack(Material.STAINED_GLASS_PANE, 1, 15))
                .setName("&r")
                .build()
        this.MENU = GenericMenu(plugin)
        this.MENU.register()
    }
}