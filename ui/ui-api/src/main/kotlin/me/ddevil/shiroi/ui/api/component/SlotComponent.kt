package me.ddevil.shiroi.ui.api.component

import me.ddevil.shiroi.ui.api.updater.Updater
import org.bukkit.inventory.ItemStack

/**
 * Represents a component that occupies a single slot, similar to a button.
 */
interface SlotComponent<U : Updater> : Drawable {
    /**
     * Checks if there is an [Updater] implementation that is able to update the icon for this component.
     *
     * @return true if [updater] is not null, false otherwise.
     */
    fun hasUpdater(): Boolean

    /**
     * An [Updater] implementation that is responsible for updating the item that is used to represent this component.
     */
    var updater: U?

    /**
     * The icon that is used to display this component in the hierarchy.
     */
    val icon: ItemStack

}
