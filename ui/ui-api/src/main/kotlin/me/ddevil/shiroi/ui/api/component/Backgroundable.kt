package me.ddevil.shiroi.ui.api.component

import org.bukkit.inventory.ItemStack

/**
 * Represents an object that can contain an [ItemStack] as a background.
 */
interface Backgroundable : Component {

    /**
     * The background of this component
     */
    var background: ItemStack?

    /**
     * @return true if there is a background set.
     */
    fun hasBackground(): Boolean
}
