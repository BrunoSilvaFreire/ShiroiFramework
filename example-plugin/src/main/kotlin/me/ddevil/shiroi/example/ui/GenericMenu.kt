package me.ddevil.shiroi.example.ui

import me.ddevil.shiroi.craft.util.ItemBuilder
import me.ddevil.shiroi.example.GenericPlugin
import me.ddevil.shiroi.ui.api.component.SlotComponent
import me.ddevil.shiroi.ui.api.component.container.MenuSize
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import me.ddevil.shiroi.ui.internal.component.CloseButton
import me.ddevil.shiroi.ui.internal.component.misc.ItemSlotComponent
import me.ddevil.shiroi.ui.internal.component.scrollable.UnderPanelScrollable
import me.ddevil.shiroi.ui.shiroi.ShiroiMenu
import me.ddevil.shiroi.ui.shiroi.ShiroiScrollerUpdater
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GenericMenu(plugin: GenericPlugin) : ShiroiMenu<GenericPlugin>(
        plugin,
        "$1Hello $2Shiroi!",
        MenuSize.SIX_ROWS,
        GenericUIConstants.PRIMARY_BACKGROUND
) {
    private val firstScrollable: UnderPanelScrollable<SlotComponent<*>> = UnderPanelScrollable(
            SlotComponent::class.java,
            this,
            4,
            3,
            ShiroiScrollerUpdater(Material.EMERALD, plugin),
            GenericUIConstants.SECONDARY_BACKGROUND,
            GenericUIConstants.PRIMARY_BACKGROUND)

    private val secondScrollable: UnderPanelScrollable<SlotComponent<*>> = UnderPanelScrollable(
            SlotComponent::class.java,
            this,
            4,
            3,
            ShiroiScrollerUpdater(Material.EMERALD, plugin),
            GenericUIConstants.SECONDARY_BACKGROUND,
            GenericUIConstants.PRIMARY_BACKGROUND)

    private val thirdScrollable: UnderPanelScrollable<SlotComponent<*>> = UnderPanelScrollable(
            SlotComponent::class.java,
            this,
            4,
            3,
            ShiroiScrollerUpdater(Material.EMERALD, plugin),
            GenericUIConstants.SECONDARY_BACKGROUND,
            GenericUIConstants.PRIMARY_BACKGROUND)


    init {
        place(firstScrollable, 0, 0)
        place(secondScrollable, 5, 0)
        place(thirdScrollable, 0, 3)
        var current = 0
        for (value in Material.values()) {
            if (value.isOccluding) {

                firstScrollable.add(ItemSlotComponent(object : ItemUpdater {
                    override fun update(oldItem: ItemStack): ItemStack {
                        return ItemBuilder(oldItem, plugin.messageManager)
                                .setName("$1${value.name}$3-$2$current")
                                .toItemStack()
                    }
                }, ItemStack(value), null))

                current++
            }
        }
        background = GenericUIConstants.PRIMARY_BACKGROUND
        place(CloseButton(ItemBuilder(Material.BARRIER, plugin.messageManager)
                .setName("$4Close")
                .toItemStack()), 8, 5)
    }

    override fun update0() {
    }
}