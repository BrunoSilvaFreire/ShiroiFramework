package me.ddevil.shiroi.ui.api.component.holder

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Backgroundable
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.area.AreaComponent

import org.bukkit.inventory.ItemStack

/**
 * A holder represents a component that is able to contain and organize other components within itself.
 *
 * These components are called "childs".
 *
 * The [Holder] class contain only one placement method, which is [add], and it places the given component
 * in the first available slot found. Other "more specific" methods must the declared in subclasses,
 * like [Container#place][me.ddevil.shiroi.ui.api.component.container.Container.place]
 * or [Scrollable#place][me.ddevil.shiroi.ui.api.component.scrollable.Scrollable.place].
 *
 * A "placement method" is a method that adds a component to the holder hierarchy
 *
 *  @author DDevil_
 */
interface Holder<D : Drawable> : AreaComponent, Backgroundable, Clickable {

    /**
     * Adds the given component in the first available slot it finds.
     *
     * This does not "re-draw" the holder, you need to specifically draw it in order to make
     * visual changes.
     *
     * @param component The component to add.
     */
    fun add(component: D)

    /**
     * Removes this component from the holder.
     *
     * This does not "re-draw" the holder, you need to specifically draw it in order to make
     * visual changes.
     */
    fun remove(component: D)

    /**
     * Removes every single component that this holder contains
     *
     * This does not "re-draw" the holder, you need to specifically draw it in order to make
     * visual changes.
     *
     */
    fun clear()

    /**
     * Checks if the given component is contained within this holder's hierarchy tree, which means, if there are
     * other holders within this holder, those holders will be checked as well.
     *
     *  @param component The component to check
     *
     * @return True if the component in within this holder's hierarchy tree, false otherwise.
     */
    operator fun contains(component: D): Boolean

    /**
     * Differently of [contains], this will check if this holder is the direct owner
     * of the component, and not possible child holders.
     */
    fun isOwnerOf(component: D): Boolean

    /**
     * Checks if the given component is currently visible (will be displayed when drawn) in this holder.
     *
     * This will always be true on containers if the component is placed within said container. On scrollables,
     * will return true if the component is placed and is on the current page of the scrollable.
     *
     * @return True if the component is currently visible (will be displayed when drawn) in this holder.
     */
    fun isVisible(component: D): Boolean

    fun isFull(): Boolean

    fun isEmpty(): Boolean

    /**
     * Checks if the component's type is allowed to be placed inside this holder.
     *
     * @return true if the component's type is allowed to be placed inside this holder.
     */
    fun accepts(component: Drawable): Boolean

    /**
     * Makes a [List] of every component contained within the holder in the order
     * that the they are placed.
     *
     * @return Every component that is contained in this area
     */
    val components: List<D>

    /**
     * Draw the changed positions by the child, other children remain untouched.
     *
     * The child is checked recursively, which means that other possible holders
     * are checked as well.
     *
     * @throws IllegalArgumentException if the holder does not [contains] the provided component.
     */
    fun drawChild(child: D): Map<UIPosition, ItemStack>

    val listeners: Set<HolderClickListener<D>>

    fun addListener(listener: HolderClickListener<D>)

    fun removeListener(listener: HolderClickListener<D>)

    /**
     * Defines if debug messages should be sent to the console when drawing this component.
     */
    var debug: Boolean
}

