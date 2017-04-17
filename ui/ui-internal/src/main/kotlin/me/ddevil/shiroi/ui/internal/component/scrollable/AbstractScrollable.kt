package me.ddevil.shiroi.ui.internal.component.scrollable

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Component
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.scrollable.Scrollable

import me.ddevil.shiroi.ui.internal.component.holder.AbstractHolder

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
        if (!contains(component)) {
            return false
        }
        return currentPage.values.contains(component)
    }

    override fun draw(fromX: Int, toX: Int, fromY: Int, toY: Int): Map<UIPosition, ItemStack> {
        val builder = ImmutableMap.Builder<UIPosition, ItemStack>()
        val addedObjects = HashSet<Component>()
        for (y in fromY .. toY) {
            for (x in fromX .. toX) {
                val currentPos = UIPosition(x, y)
                val uiObject = currentPage[currentPos.toInvSlot(width)]
                if (uiObject != null) {
                    if (!addedObjects.contains(uiObject)) {
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