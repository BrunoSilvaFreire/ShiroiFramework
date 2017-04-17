package me.ddevil.shiroi.ui.api.component.area


import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import java.util.*


/**
 * Represents a [me.ddevil.shiroi.ui.api.component.Component] that can hold more than one minecraft slots inside a menu.
 */
interface AreaComponent : Drawable {
    /**
     * Redefines the area to fit the desired sizes.

     * @param width  The new width
     * *
     * @param height The new height
     */
    fun redefineArea(width: Int, height: Int)

    /**
     * @return A set sorted by the minecraft slot position with all the UIPositions contained by this object.
     */
    val area: SortedSet<UIPosition>

    /**
     * Checks if the given position is within the relative area of this object.

     * @param position The position to check
     * *
     * @return true if the given position is within bounds, false otherwise.
     */
    operator fun contains(position: UIPosition): Boolean

    fun contains(x: Int, y: Int): Boolean

    /**
     * @return The total number of slots that this object contains.
     */
    val size: Int

    /**
     * @return The maximum relative X of this object
     */
    val maxX: Int

    /**
     * @return The maximum relative Y of this object
     */
    val maxY: Int

    /**
     * @return The width of the object
     */
    val width: Int

    /**
     * @return The height of the object
     */
    val height: Int
}
