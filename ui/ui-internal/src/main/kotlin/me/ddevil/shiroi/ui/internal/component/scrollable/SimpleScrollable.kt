package me.ddevil.shiroi.ui.internal.component.scrollable

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.Component
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.scrollable.Scrollable
import me.ddevil.shiroi.ui.api.event.UIActionEvent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import org.bukkit.inventory.ItemStack
import java.util.*

open class SimpleScrollable<H : Drawable>(
        expectedType: Class<H>,
        width: Int,
        height: Int,
        background: ItemStack?,
        id: String?,
        var onClickEmptyAction: Action?) : AbstractScrollable<H>(
        expectedType,
        width,
        height,
        background,
        id), Scrollable<H> {

    override var currentIndex = 0
    private val positionMap: SortedMap<Int, H>
    final override val currentPage: SortedMap<Int, H>
    final override var action: Action = ScrollableClickHandler()

    init {
        if (height <= 0 || width <= 0) {
            throw IllegalArgumentException("The height and width must be larger than 0!")
        }
        this.positionMap = TreeMap<Int, H>()
        this.currentPage = TreeMap<Int, H>()
        this.action = ScrollableClickHandler()
    }

    private inner class ScrollableClickHandler : Action {

        override fun invoke(e: UIClickEvent, localPosition: UIPosition) {
            val clickedSlot = e.clickedSlot.toInvSlot()
            if (currentPage.containsKey(clickedSlot)) {
                val obj = currentPage[clickedSlot]
                if (obj is Clickable) {
                    val c = obj
                    c.action.invoke(e, UIPosition.ZERO)
                    UIActionEvent(c, e.clickedSlot, e.player, e.clickType, e.placedBlock).call()
                }
            } else if (onClickEmptyAction != null) {
                onClickEmptyAction?.invoke(e, UIPosition.ZERO)
            }
        }
    }

    override fun update() {
        currentPage.values.forEach { it.update() }
    }


    override fun getCurrentPositionOf(child: H): UIPosition {
        for ((pos, component) in currentPage.entries) {
            if (component == child) {
                return UIPosition.fromSlot(pos, width)
            }
        }
        throw IllegalStateException("Holder does not contain child $child!")
    }


    override fun add(component: H) {
        for (i in 0 .. Integer.MAX_VALUE - 1) {
            if (canPlaceIn(i)) {
                place(component, i)
                break
            }
        }
    }

    override fun clear() {
        this.positionMap.clear()
    }

    override fun isOwnerOf(obj: H): Boolean {
        return positionMap.containsValue(obj)
    }

    override fun hasObjectInSlot(position: Int): Boolean {
        return currentPage.containsKey(position)
    }

    fun getDrawable(position: Int): H? {
        return currentPage[position]
    }

    override fun get(position: Int) = positionMap[position]

    override fun contains(component: H) = positionMap.values.contains(component)

    val allDrawables: List<H>
        get() = ArrayList(positionMap.values)

    override fun hasBackground(): Boolean {
        return background != null
    }

    override fun place(drawable: H, position: Int) {
        if (canPlaceIn(position)) {
            synchronized(positionMap) {
                positionMap.put(position, drawable)
            }
            recalculateCurrentPage()
        } else {
            throw IllegalStateException("There is already an object in position $position!")
        }
    }

    override fun remove(component: H) {
        if (isOwnerOf(component)) {
            positionMap.remove(get(component))
        }
    }

    override fun remove(position: Int) {
        if (hasObjectInSlot(position)) {
            positionMap.remove(position)
        }
    }

    private fun recalculateCurrentPage() {
        currentPage.clear()
        val pageSize = pageSize
        val start = currentIndex * pageSize
        val end = start + pageSize - 1
        for ((pos, i) in (start .. end).withIndex()) {
            val get = positionMap[i]
            if (get != null) {
                currentPage.put(pos, get)
                get.update()
            }
        }
    }

    override val componentMap: Map<Int, H>
        get() = TreeMap(currentPage)

    override val components: List<H>
        get() = positionMap.values.filterNotNull()

    override fun canPlaceIn(position: Int) = !positionMap.containsKey(position)

    override val totalPages: Int
        get() {
            val lastSlot = lastSlot
            if (lastSlot == 0) {
                return 1
            }
            return lastSlot / pageSize + 1
        }

    override val lastSlot: Int
        get() {
            if (positionMap.isEmpty()) {
                return 0
            }
            return positionMap.lastKey()
        }

    override val pageSize: Int
        get() = width * height

    override fun scroll(direction: ScrollDirection) {
        val page = currentIndex + direction.scrollQuantity
        if (page >= totalPages || page < 0) {
            return
        }
        currentIndex += direction.scrollQuantity
        recalculateCurrentPage()
    }

    override fun goToPage(page: Int) {
        var page = page
        if (page >= totalPages) {
            page = totalPages - 1
        } else if (page < 0) {
            page = 0
        }
        this.currentIndex = page
        recalculateCurrentPage()
    }

    override fun get(drawable: H): Int {
        if (positionMap.containsValue(drawable)) {
            return positionMap
                    .filter { getDrawable(it.key) == drawable }
                    .minBy { e -> e.key!! }!!.key
        }
        throw IllegalArgumentException("There is no drawable $drawable in this scrollable!")
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

    override fun draw() = draw(0, width - 1, 0, height - 1)

}
