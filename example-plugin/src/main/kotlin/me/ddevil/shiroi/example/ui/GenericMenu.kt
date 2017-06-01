package me.ddevil.shiroi.example.ui

import me.ddevil.shiroi.craft.util.ShiroiItemBuilder
import me.ddevil.shiroi.example.GenericPlugin
import me.ddevil.shiroi.ui.api.component.CloseButton
import me.ddevil.shiroi.ui.api.component.SlotComponent
import me.ddevil.shiroi.ui.api.component.container.MenuSize
import me.ddevil.shiroi.ui.api.component.misc.ItemSlotComponent
import me.ddevil.shiroi.ui.api.component.scrollable.SimpleScrollable
import me.ddevil.shiroi.ui.api.component.scrollable.UnderPanelScrollable
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import me.ddevil.shiroi.ui.shiroi.ShiroiMenu
import me.ddevil.shiroi.ui.shiroi.ShiroiScrollerUpdater
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GenericMenu(plugin: GenericPlugin) : ShiroiMenu<GenericPlugin>(
        plugin,
        "$1Hello $2world!",
        MenuSize.SIX_ROWS,
        GenericUIConstants.PRIMARY_BACKGROUND
) {
    private val firstScrollable = SimpleScrollable(
            SlotComponent::class.java,
            4,
            3,
            GenericUIConstants.SECONDARY_BACKGROUND)

    private val secondScrollable = UnderPanelScrollable(
            SlotComponent::class.java,
            this,
            4,
            3,
            ShiroiScrollerUpdater(Material.DIAMOND, plugin),
            GenericUIConstants.SECONDARY_BACKGROUND,
            GenericUIConstants.PRIMARY_BACKGROUND)

    private val thirdScrollable: UnderPanelScrollable<SlotComponent<*>> = UnderPanelScrollable(
            SlotComponent::class.java,
            this,
            4,
            3,
            ShiroiScrollerUpdater(Material.REDSTONE, plugin),
            GenericUIConstants.SECONDARY_BACKGROUND,
            GenericUIConstants.PRIMARY_BACKGROUND)


    init {
        debug = true
        place(firstScrollable, 0, 0)
        place(secondScrollable, 5, 0)
        for ((index, value) in Material.values().withIndex()) {
            if (value.isOccluding) {
                firstScrollable.add(
                        ItemSlotComponent(
                                object : ItemUpdater {
                                    override fun update(oldItem: ItemStack): ItemStack {
                                        return ShiroiItemBuilder(plugin.messageManager, oldItem)
                                                .setName("$1${value.name}$3-$2$index")
                                                .build()
                                    }
                                }, ItemStack(value), null)
                )
            }
        }
        this.background = GenericUIConstants.PRIMARY_BACKGROUND
        place(CloseButton(ShiroiItemBuilder(plugin.messageManager, Material.BARRIER)
                .setName("$4Close")
                .build()), 8, 5)
    }

    override fun update0() {
    }
}