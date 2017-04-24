package me.ddevil.shiroi.ui.internal.component.misc

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.SlotComponent
import me.ddevil.shiroi.ui.api.updater.Updater
import org.bukkit.inventory.ItemStack

abstract class AbstractSlotComponent<U : Updater>
    : AbstractComponent, SlotComponent<U> {


    final override var icon: ItemStack
    final override var updater: U?

    @JvmOverloads
    constructor(updater: U, initialIcon: ItemStack, id: String? = null) : super(id) {
        this.icon = initialIcon
        this.updater = updater
    }

    constructor(icon: ItemStack, id: String?) : super(id) {
        this.icon = icon
        this.updater = null
    }

    override fun update() {
        if (hasUpdater()) {
            this.icon = updateIcon(updater!!)
        }
    }

    override fun draw(): Map<UIPosition, ItemStack> = ImmutableMap.Builder<UIPosition, ItemStack>()
            .put(UIPosition.ZERO, icon)
            .build()

    override fun hasUpdater() = updater != null
    protected abstract fun updateIcon(updater: U): ItemStack

    override fun draw(from: UIPosition, to: UIPosition) = draw(from.x, to.x, from.y, to.y)

    override fun draw(fromX: Int, toX: Int, fromY: Int, toY: Int): Map<UIPosition, ItemStack> {
        if (fromX > 0 || toX <= 0 || fromX > 1 || toX <= 1) {
            return emptyMap()
        }
        return draw()
    }
}