package me.ddevil.shiroi.ui.api.component.menu

import me.ddevil.shiroi.ui.api.MAX_INVENTORY_SIZE_X
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.container.ArrayContainer
import me.ddevil.shiroi.ui.api.component.container.Menu
import me.ddevil.shiroi.ui.api.component.container.MenuSize
import me.ddevil.shiroi.ui.api.component.holder.HolderClickListener
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

abstract class ContainerMenu<out P : Plugin>
@JvmOverloads
constructor(
        final override val plugin: P,
        title: String? = null,
        size: MenuSize = MenuSize.SIX_ROWS,
        background: ItemStack? = null,
        id: String? = null
) : ArrayContainer<Drawable>(Drawable::class.java, MAX_INVENTORY_SIZE_X, size.rows, background, id), Menu<P> {

    final override var inventory = Bukkit.createInventory(null, size.rows * 9, title)!!

    init {
        addListener(object : HolderClickListener<Drawable> {
            override fun onClick(drawable: Drawable?,
                                 event: UIClickEvent) {
                drawChild(drawable ?: return)
            }
        })
    }

    override fun update() {
        super.update()
        placeItems(draw())
        update0()
    }

    private fun placeItems(items: Map<UIPosition, ItemStack>) {
        items.forEach { inventory.setItem(it.key.toInvSlot(width), it.value) }
    }

    override fun updateIfHasViewer() {
        if (inventory.viewers?.isNotEmpty() ?: false) {
            update()
        }
    }

    override fun open(p: Player) {
        update()
        p.openInventory(inventory)
    }

    override fun drawChild(child: Drawable): Map<UIPosition, ItemStack> {
        val childmap = super.drawChild(child)
        placeItems(childmap)
        return childmap
    }


}