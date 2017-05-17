package me.ddevil.shiroi.ui.api.component.scrollable

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Component
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.holder.AbstractHolder
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class AbstractScrollable<D : Drawable>
constructor(
        expectedType: Class<D>,
        width: Int,
        height: Int,
        background: ItemStack? = null,
        id: String? = null
) : AbstractHolder<D>(expectedType, width, height, background, id), Scrollable<D> {

    override fun isVisible(component: D): Boolean {
        if (component !in this) {
            return false
        }
        return component in currentPage.values
    }

    override fun set(position: Int, component: D) = place(component, position)

    override fun draw(fromX: Int, toX: Int, fromY: Int, toY: Int): Map<UIPosition, ItemStack> {
        val builder = ImmutableMap.Builder<UIPosition, ItemStack>()
        val addedObjects = HashSet<Component>()
        for (y in fromY..toY) {
            for (x in fromX..toX) {
                val currentPos = UIPosition(x, y)
                val uiObject = currentPage[currentPos.toInvSlot(width)]
                if (uiObject != null) {
                    if (uiObject !in addedObjects) {
                        addedObjects.add(uiObject)
                        val childUI = uiObject.draw()
                        childUI.forEach { builder.put(it.key + currentPos, it.value) }
                    }
                } else if (hasBackground()) {
                    builder.put(currentPos, background)
                }
            }
        }
        return builder.build()
    }
}