package me.ddevil.shiroi.ui.api.component.container

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.holder.Holder
import java.util.*

/**
 * A container represents a holder that is displayed in a single page, for example, [Menu]s.
 */
interface Container<H : Drawable> : Holder<H> {
    /**
     * Places the specified [drawable] in the [position]
     * @throws me.ddevil.shiroi.ui.api.exception.PositionAlreadyOccupiedException if there is already a object in the specified [position]
     * @throws me.ddevil.shiroi.ui.api.exception.OutOfBoundsException if the container does not [contains] the specified [position]
     */
    fun place(drawable: H, position: UIPosition)

    /**
     *  Places the specified [drawable] in the specified coordinates ([x], [y])
     * @throws me.ddevil.shiroi.ui.api.exception.PositionAlreadyOccupiedException if there is already a object in the specified coordinates ([x], [y])
     * @throws me.ddevil.shiroi.ui.api.exception.OutOfBoundsException if the container does not [contains] the specified coordinates ([x], [y])
     */
    fun place(drawable: H, x: Int, y: Int)

    /**
     *
     */
    fun canPlaceIn(position: UIPosition): Boolean

    fun canPlaceIn(x: Int, y: Int): Boolean

    fun remove(position: UIPosition): H?

    fun remove(x: Int, y: Int): H?

    fun hasObjectIn(position: UIPosition): Boolean

    fun hasObjectIn(x: Int, y: Int): Boolean

    operator fun get(position: UIPosition): H?

    operator fun get(x: Int, y: Int): H?

    fun getMenuMap(): SortedMap<UIPosition, H>

    fun getPosition(drawable: H): UIPosition?

}