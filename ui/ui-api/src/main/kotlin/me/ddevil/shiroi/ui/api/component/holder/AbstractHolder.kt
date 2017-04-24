package me.ddevil.shiroi.ui.internal.component.holder

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.holder.Holder
import me.ddevil.shiroi.ui.api.component.holder.HolderClickListener
import me.ddevil.shiroi.ui.internal.component.area.AbstractAreaComponent
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class AbstractHolder<D : Drawable>
constructor(
        private var expectedType: Class<D>,
        width: Int,
        height: Int,
        override var background: ItemStack?,
        id: String?
) : AbstractAreaComponent(width, height, id), Holder<D> {
    private val _listeners: MutableSet<HolderClickListener<D>> = mutableSetOf()

    override val listeners: Set<HolderClickListener<D>>
        get() = HashSet(_listeners)

    override fun hasBackground() = background != null

    override fun accepts(component: Drawable) = component.javaClass.isAssignableFrom(expectedType)

    override fun contains(component: D): Boolean {
        return checkContainsRecursive(this, component)
    }

    private fun checkContainsRecursive(holder: Holder<D>, component: D): Boolean {
        if (holder.isOwnerOf(component)) {
            return true
        }
        return holder.components
                .filter { it is Holder<*> && it.accepts(component) }
                .map { checkContainsRecursive(it as Holder<D>, component) }
                .any { it }
    }

    override fun drawChild(child: D): Map<UIPosition, ItemStack> {
        if (!contains(child)) {
            throw IllegalArgumentException("This holder doesn't contains component $child in it's hierarchy!")
        }
        if (!isVisible(child)) {
            return emptyMap()
        }
        val currentPos = this.getCurrentPositionOf(child)
        return child.draw().mapKeys {
            return@mapKeys it.key + currentPos
        }
    }

    abstract fun getCurrentPositionOf(child: D): UIPosition

    override fun draw(): Map<UIPosition, ItemStack> {
        return draw(0, width - 1, 0, height - 1)
    }

    override fun draw(from: UIPosition, to: UIPosition): Map<UIPosition, ItemStack> {
        return draw(from.x, to.x, from.y, to.y)
    }


    override fun isFull() = components.size >= size

    override fun isEmpty() = components.isEmpty()
    override fun isOwnerOf(component: D) = components.contains(component)

    override fun addListener(listener: HolderClickListener<D>) {
        _listeners.add(listener)
    }

    override fun removeListener(listener: HolderClickListener<D>) {
        _listeners.remove(listener)
    }
}