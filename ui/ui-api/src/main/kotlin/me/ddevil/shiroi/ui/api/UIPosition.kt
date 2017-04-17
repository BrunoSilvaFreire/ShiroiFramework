package me.ddevil.shiroi.ui.api

import me.ddevil.util.vector.IntVector2

/**
 * Represents a position in a [me.ddevil.shiroi.ui.api.component.container.Container].
 * @see me.ddevil.shiroi.ui.api.component.container.Container
 */
class UIPosition(x: Int, y: Int) : IntVector2(x, y) {


    @JvmOverloads
    fun toInvSlot(width: Int = MAX_INVENTORY_SIZE_X) = y * width + x

    operator fun compareTo(other: UIPosition): Int = other.toInvSlot().compareTo(toInvSlot())

    operator fun plus(position: UIPosition) = UIPosition(x + position.x, y + position.y)

    operator fun minus(position: UIPosition) = UIPosition(x - position.x, y - position.y)

    override fun toString() = "UIPosition{x=$x, y=$y}"

    companion object {
        @JvmOverloads
        fun fromSlot(slot: Int, parentWidth: Int = MAX_INVENTORY_SIZE_X) = UIPosition(slot % parentWidth,
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