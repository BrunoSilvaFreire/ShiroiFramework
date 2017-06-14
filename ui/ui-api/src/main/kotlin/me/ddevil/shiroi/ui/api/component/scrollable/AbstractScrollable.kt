package me.ddevil.shiroi.ui.api.component.scrollable

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.holder.AbstractHolder
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class AbstractScrollable<D : Drawable>
constructor(
        expectedType: Class<D>,
        width: Int,
        height: Int,
        final override val pageWidth: Int,
        final override val pageHeight: Int,
        background: ItemStack? = null,
        id: String? = null
) : AbstractHolder<D>(expectedType, width, height, background, id), Scrollable<D> {

    //Pages
    private val positionMap: SortedMap<Int, D>
    final override val currentPage: SortedMap<Int, D>
    override val pageSize = pageWidth * pageHeight

    init {
        if (height <= 0 || width <= 0) {
            throw IllegalArgumentException("The height and width must be larger than 0!")
        }
        if (pageWidth > width || pageHeight > height) {
            throw IllegalArgumentException("The page size cannot be larger than the Scrollable itself!")
        }
        this.positionMap = TreeMap<Int, D>()
        this.currentPage = TreeMap<Int, D>()
    }

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

    override var currentIndex = 0
        set(value) {
            var page = value
            if (page >= totalPages) {
                page = totalPages - 1
            } else if (page < 0) {
                page = 0
            }
            field = page
            recalculateCurrentPage()
        }

    private fun recalculateCurrentPage() {
        currentPage.clear()
        val start = currentIndex * pageSize
        val end = start + pageSize - 1
        for ((pos, i) in (start..end).withIndex()) {
            val obj = positionMap[i]
            if (obj != null) {
                currentPage[pos] = obj
                obj.update()
            }
        }
    }

    override fun update() {
        for (it in currentPage.values) {
            it.update()
        }
    }


    override fun getCurrentPositionOf(child: D): UIPosition {
        for ((pos, component) in currentPage.entries) {
            if (component == child) {
                return UIPosition.fromSlot(pos, width)
            }
        }
        throw IllegalStateException("Holder does not contain child $child!")
    }


    override fun add(component: D) {
        for (i in 0..Integer.MAX_VALUE - 1) {
            if (canPlaceIn(i)) {
                place(component, i)
                break
            }
        }
    }

    override fun clear() {
        this.positionMap.clear()
    }

    override fun isOwnerOf(obj: D): Boolean {
        return positionMap.containsValue(obj)
    }

    override fun hasObjectInSlot(position: Int): Boolean {
        return currentPage.containsKey(position)
    }

    fun getDrawable(position: Int): D? {
        return currentPage[position]
    }

    override fun get(position: Int) = positionMap[position]

    override fun contains(component: D) = positionMap.values.contains(component)

    val allDrawables: List<D>
        get() = ArrayList(positionMap.values)

    override fun hasBackground(): Boolean {
        return background != null
    }

    override fun place(component: D, position: Int) {
        if (canPlaceIn(position)) {
            positionMap[position] = component
            component.update()
            recalculateCurrentPage()
        } else {
            throw IllegalStateException("There is already an object in position $position!")
        }
    }

    override fun remove(component: D) {
        if (isOwnerOf(component)) {
            positionMap.remove(get(component))
        }
    }

    override fun remove(position: Int) {
        if (hasObjectInSlot(position)) {
            positionMap.remove(position)
        }
    }


    override val componentMap: Map<Int, D>
        get() = TreeMap(currentPage)

    override val components: List<D>
        get() = positionMap.values.filterNotNull()

    override fun isVisible(component: D): Boolean {
        if (component !in this) {
            return false
        }
        return component in currentPage.values
    }

    override fun set(position: Int, component: D) = place(component, position)

    override fun scroll(direction: ScrollDirection) {
        val page = currentIndex + direction.scrollQuantity
        if (page >= totalPages || page < 0) {
            return
        }
        currentIndex += direction.scrollQuantity
        recalculateCurrentPage()
    }

    override fun goToPage(page: Int) {
        this.currentIndex = page
    }

    override fun get(drawable: D): Int {
        if (positionMap.containsValue(drawable)) {
            return positionMap
                    .filter { getDrawable(it.key) == drawable }
                    .minBy { e -> e.key!! }!!.key
        }
        throw IllegalArgumentException("There is no drawable $drawable in this scrollable!")
    }

}