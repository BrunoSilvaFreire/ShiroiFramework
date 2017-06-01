package me.ddevil.shiroi.ui.api.component.scrollable

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.Component
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.event.UIActionEvent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import org.bukkit.inventory.ItemStack
import java.util.*

class SimpleScrollable<D : Drawable>
@JvmOverloads
constructor(
        expectedType: Class<D>,
        width: Int,
        height: Int,
        background: ItemStack? = null,
        id: String? = null
) : AbstractScrollable<D>(expectedType, width, height, width, height, background, id), Scrollable<D> {

    private inner class SimpleScrollableClickHandler : Action {

        override fun invoke(e: UIClickEvent, localPosition: UIPosition) {
            val clickedSlot = e.clickedSlot.toInvSlot()
            var obj: D? = null
            if (clickedSlot in currentPage) {
                obj = currentPage[clickedSlot]
                if (obj is Clickable) {
                    val c = obj
                    c.action.invoke(e, UIPosition.ZERO)
                    UIActionEvent(c, e.clickedSlot, e.player, e.clickType, e.placedBlock).call()
                }
            }
            for (listener in listeners) {
                listener.onClick(obj)
            }
        }
    }

    override var action: Action = SimpleScrollableClickHandler()

    override fun draw(fromX: Int, toX: Int, fromY: Int, toY: Int): Map<UIPosition, ItemStack> {
        val builder = ImmutableMap.Builder<UIPosition, ItemStack>()
        val addedObjects = HashSet<Component>()
        if (debug) {
            println("Drawing component '$this'")
        }
        for (y in fromY..toY) {
            for (x in fromX..toX) {
                val currentPos = UIPosition(x, y)
                val uiObject = currentPage[currentPos.toInvSlot(width)]
                if (uiObject != null) {
                    if (uiObject !in addedObjects) {
                        addedObjects.add(uiObject)
                        val childUI = uiObject.draw()
                        childUI.forEach { builder.put(it.key + currentPos, it.value) }

                        if (debug) {
                            println("Drawing child component $uiObject  @ $currentPos")
                        }
                    } else {
                        println("Found object $uiObject  @ $currentPos, but was already drawn, skipping.")
                    }
                } else if (hasBackground()) {
                    builder.put(currentPos, background)

                    if (debug) {
                        println("No component @ $currentPos, using background.")
                    }
                } else if (debug) {
                    println("No component @ $currentPos and no background.")
                }
            }
        }
        return builder.build()
    }

    override fun draw() = draw(0, width - 1, 0, height - 1)

}
