package me.ddevil.shiroi.ui.shiroi

import me.ddevil.shiroi.craft.message.MessageColor
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.shiroi.craft.util.ItemBuilder
import me.ddevil.shiroi.ui.api.DEFAULT_NEXT_TEXT
import me.ddevil.shiroi.ui.api.DEFAULT_PREVIOUS_TEXT
import me.ddevil.shiroi.ui.api.component.scrollable.Scrollable
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import me.ddevil.shiroi.ui.api.updater.ScrollerUpdater
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ShiroiScrollerUpdater : ScrollerUpdater<Scrollable<*>> {

    private val icon: ItemStack
    private val previous: String
    private val next: String
    private val messageManager: MessageManager

    constructor(icon: Material, plugin: ShiroiPlugin<*, *>) : this(ItemStack(icon), plugin)


    constructor (icon: ItemStack, plugin: ShiroiPlugin<*, *>) : this(icon,
            plugin,
            DEFAULT_NEXT_TEXT,
            DEFAULT_PREVIOUS_TEXT)


    constructor(icon: ItemStack, plugin: ShiroiPlugin<*, *>, next: String, previous: String) {
        this.icon = icon
        this.messageManager = plugin.messageManager
        this.next = next
        this.previous = previous
    }

    override fun update(scrollable: Scrollable<*>, direction: ScrollDirection): ItemStack {
        return ItemBuilder(ItemStack(icon), messageManager)
                .setName(MessageColor.PRIMARY.toString() + if (direction === ScrollDirection.NEXT) next else previous)
                .setLore(listOf(MessageColor.PRIMARY.toString() + (scrollable.currentIndex + 1) + MessageColor.NEUTRAL + "/" + MessageColor.SECONDARY + scrollable.totalPages))
                .toItemStack()
    }
}