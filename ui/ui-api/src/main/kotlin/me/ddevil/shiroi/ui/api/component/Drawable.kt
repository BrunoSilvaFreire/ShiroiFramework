package me.ddevil.shiroi.ui.api.component

import me.ddevil.shiroi.ui.api.UIPosition
import org.bukkit.inventory.ItemStack

/**
 * This represents a component that has an actual body. In other words, a component that  can be displayed in a
 * [me.ddevil.shiroi.ui.api.component.container.Menu] with [ItemStack]s.
 */
interface Drawable : Component {
    /**
     * Draws this component only on the delimited area
     */
    fun draw(from: UIPosition, to: UIPosition): Map<UIPosition, ItemStack>

    /**
     * Draws this component only on the delimited area
     */
    fun draw(fromX: Int, toX: Int, fromY: Int, toY: Int): Map<UIPosition, ItemStack>

    /**
     * Draws this component completely.
     */
    fun draw(): Map<UIPosition, ItemStack>

}