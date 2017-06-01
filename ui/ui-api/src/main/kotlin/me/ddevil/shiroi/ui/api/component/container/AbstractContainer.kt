package me.ddevil.shiroi.ui.api.component.container

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Component
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.area.AreaComponent
import me.ddevil.shiroi.ui.api.component.holder.AbstractHolder
import me.ddevil.shiroi.ui.api.exception.OutOfBoundsException
import me.ddevil.shiroi.ui.api.exception.PositionAlreadyOccupiedException
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.misc.handler.ContainerHandler
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class AbstractContainer<D : Drawable>
constructor(
        expectedType: Class<D>,
        width: Int,
        height: Int,
        background: ItemStack?,
        id: String?
) : AbstractHolder<D>(expectedType, width, height, background, id), Container<D> {

    //<editor-fold defaultstate="collapsed" desc="Click Handling">
    private val handler: Action

    override val action: Action
        get() = handler

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Removing methods">
    override fun remove(component: D) {
        remove(getPosition(component) ?: throw IllegalStateException("Couldn't find position for component $component"))
    }

    protected abstract fun remove0(position: UIPosition)

    override fun remove(x: Int, y: Int): D? {
        if (contains(x, y) && hasObjectIn(x, y)) {
            val d = get(x, y)
            remove0(x, y)
            return d
        }
        return null
    }

    protected abstract fun remove0(x: Int, y: Int)
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Update methods">
    override fun update() {
        components.forEach { it.update() }
        update0()
    }

    open fun update0() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Placement methods">
    override fun add(component: D) {
        for (i in 0..size - 1) {
            val pos = UIPosition.fromSlot(i, width)
            if (canPlaceIn(pos)) {
                place(component, pos)
                return
            }
        }
        throw IllegalStateException("There are no slots available!")
    }

    final override fun place(drawable: D, x: Int, y: Int) {
        if (contains(x, y)) {
            val found = get(x, y)
            if (found == null) {
                if (drawable is AreaComponent) {
                    addAreaObject(drawable, x, y)
                } else {
                    place0(drawable, x, y)
                }
            } else {
                throw IllegalArgumentException("There is already a different object in position $x, $y ($found)!")
            }
        } else {
            throw OutOfBoundsException(this, UIPosition(x, y))
        }
    }

    override fun place(drawable: D, position: UIPosition) {
        if (contains(position)) {
            val found = get(position)
            if (found == null) {
                if (drawable is AreaComponent) {
                    addAreaObject(drawable, position)
                } else {
                    place0(drawable, position)
                }
            } else {
                throw PositionAlreadyOccupiedException(position, found)
            }
        } else {
            throw OutOfBoundsException(this, position)
        }
    }

    protected abstract fun place0(drawable: D, position: UIPosition)

    protected abstract fun place0(drawable: D, x: Int, y: Int)

    override fun canPlaceIn(position: UIPosition) = !hasObjectIn(position)

    override fun canPlaceIn(x: Int, y: Int) = !hasObjectIn(x, y)
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Area Object Placement Handling">
    private fun addAreaObject(drawable: AreaComponent, position: UIPosition) {
        addAreaObject(drawable, position.x, position.y)
    }

    private fun addAreaObject(drawable: AreaComponent, x: Int, y: Int) {
        for (ix in 0..drawable.width - 1) {
            for (iy in 0..drawable.height - 1) {
                val finalX = ix + x
                val finalY = iy + y
                if (hasObjectIn(finalX, finalY)) {
                    val found = this[finalX, finalY]
                    if (found != drawable) {
                        throw IllegalArgumentException("There is already a different object in position $x, $y ($found)!")
                    }
                }
                place0(drawable as D, finalX, finalY)
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Containment Handling">

    override fun remove(position: UIPosition) = remove(position.x, position.y)

    //</editor-fold>
    init {
        this.handler = ContainerHandler(this) { drawable ->
            for (listener in listeners) {
                listener.onClick(drawable)
            }
        }
    }


    /**
     * Checks if the given component is currently visible (will be displayed when drawn) in this holder.
     *
     * This will always be true on containers if the component is placed within said container. On scrollables,
     * will return true if the component is placed and is on the current page of the scrollable.
     *
     * @return True if the component is currently visible (will be displayed when drawn) in this holder.
     */
    override fun isVisible(component: D) = component in this

    override fun getCurrentPositionOf(child: D): UIPosition = getPosition(child) ?: throw IllegalStateException("Holder does not contain child $child!")

    override fun draw(fromX: Int, toX: Int, fromY: Int, toY: Int): Map<UIPosition, ItemStack> {
        val objMap = getMenuMap()
        val builder = ImmutableMap.Builder<UIPosition, ItemStack>()
        val addedObjects = HashSet<Component>()
        if (debug) {
            println("Drawing component '$this'")
            println("Object Map: $objMap")
        }
        for (y in fromY..toY) {
            for (x in fromX..toX) {
                val currentPos = UIPosition(x, y)
                val uiObject = this[x, y]
                if (uiObject != null) {
                    if (uiObject !in addedObjects) {
                        addedObjects.add(uiObject)
                        val childUI = uiObject.draw()
                        for ((childPos, item) in childUI) {
                            builder.put(currentPos + childPos, item)
                        }
                        if (debug) {
                            println("Drawing child component $uiObject  @ $currentPos")
                        }
                    } else {
                        println("Found object $uiObject  @ $currentPos, but was already drawn, skipping.")
                    }
                } else if (hasBackground()) {
                    builder.put(currentPos, background!!)
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

}