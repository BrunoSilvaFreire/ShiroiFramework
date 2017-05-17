package me.ddevil.shiroi.ui.api

import me.ddevil.shiroi.ui.api.component.container.Container
import me.ddevil.util.vector.IntVector2

/**
 * Represents a position in a [me.ddevil.shiroi.ui.api.component.container.Container].
 * @see me.ddevil.shiroi.ui.api.component.container.Container
 */
class UIPosition(x: Int, y: Int) : IntVector2(x, y) {

    /**
     * Converts this position to a int representing the slot it would be located in
     * a container with the specified [width].
     * By default, it uses the normal minecraft inventory as the width ([MAX_INVENTORY_SIZE_X]), which is 9
     */
    @JvmOverloads
    fun toInvSlot(width: Int = MAX_INVENTORY_SIZE_X) = y * width + x

    /**
     * Converts this position to a int representing the slot it would be located in
     * the specified [container]
     */
    fun toInvSlot(container: Container<*>) = toInvSlot(container.width)

    operator fun compareTo(other: UIPosition): Int = other.toInvSlot().compareTo(toInvSlot())

    operator fun plus(position: UIPosition) = UIPosition(x + position.x, y + position.y)

    operator fun minus(position: UIPosition) = UIPosition(x - position.x, y - position.y)

    override fun toString() = "UIPosition{x=$x, y=$y}"

    companion object {
        @JvmOverloads
        fun fromSlot(slot: Int, parentWidth: Int = MAX_INVENTORY_SIZE_X) = UIPosition(
                slot % parentWidth,
                slot / parentWidth)

        val ZERO = UIPosition(0, 0)
        val LEFT = UIPosition(-1, 0)
        val RIGHT = UIPosition(1, 0)

        val UP_LEFT = UIPosition(-1, 1)
        val UP_RIGHT = UIPosition(1, 1)
        val UP = UIPosition(0, 1)

        val DOWN = UIPosition(0, -1)
        val DOWN_LEFT = UIPosition(-1, -1)
        val DOWN_RIGHT = UIPosition(1, -1)

        val ONE = UP_RIGHT
    }
}