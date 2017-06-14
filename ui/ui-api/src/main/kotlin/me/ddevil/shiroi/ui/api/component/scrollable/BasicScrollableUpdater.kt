package me.ddevil.shiroi.ui.api.component.scrollable

import me.ddevil.shiroi.ui.api.DEFAULT_NEXT_TEXT
import me.ddevil.shiroi.ui.api.DEFAULT_PREVIOUS_TEXT
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import me.ddevil.shiroi.ui.api.updater.ScrollerUpdater
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * Created by bruno on 04/11/2016.
 */
class BasicScrollableUpdater(private val icon: ItemStack,
                             private val next: String,
                             private val previous: String,
                             private val primary: ChatColor,
                             private val secondary: ChatColor,
                             private val neutral: ChatColor) : ScrollerUpdater<Scrollable<*>> {


    constructor(icon: Material, primary: ChatColor, secondary: ChatColor, neutral: ChatColor) : this(ItemStack(icon),
            primary,
            secondary,
            neutral)

    constructor(icon: ItemStack, primary: ChatColor, secondary: ChatColor, neutral: ChatColor) : this(icon,
            DEFAULT_NEXT_TEXT,
            DEFAULT_PREVIOUS_TEXT,
            primary,
            secondary,
            neutral)

    constructor(icon: Material,
                next: String,
                previous: String,
                primary: ChatColor,
                secondary: ChatColor,
                neutral: ChatColor) : this(ItemStack(icon), next, previous, primary, secondary, neutral)

    override fun update(oldIcon: ItemStack,
                        scrollable: Scrollable<*>,
                        direction: ScrollDirection): ItemStack {
        val item = ItemStack(oldIcon)
        val itemMeta = item.itemMeta
        itemMeta.displayName = primary.toString() + if (direction == ScrollDirection.NEXT) next else previous
        itemMeta.lore = listOf(primary.toString() + (scrollable.currentIndex + 1) + neutral + "/" + secondary + scrollable.totalPages)
        item.itemMeta = itemMeta
        return item
    }
}
