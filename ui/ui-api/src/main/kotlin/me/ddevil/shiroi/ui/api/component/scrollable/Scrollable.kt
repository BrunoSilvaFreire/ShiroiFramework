package me.ddevil.shiroi.ui.api.component.scrollable

import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.holder.Holder
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import java.util.*

/**
 * Represents a [Holder] that can contain one or more pages, where each one of them can contain more [Drawable].
 * Think of it as like a TabHost on android.
 *
 * Contrary to a [me.ddevil.shiroi.ui.api.component.container.Container], a scrollable does not use
 * [me.ddevil.shiroi.ui.api.UIPosition] as it's index. It uses integer numbers in the order in which it's components is
 * displayed.
 */
interface Scrollable<D : Drawable> : Holder<D> {

    /**
     * A map that contains all of the components in this [Scrollable] and it's indexes.
     */
    val componentMap: Map<Int, D>
    /**
     * The index of the current page.
     */
    var currentIndex: Int

    /**
     * The total of pages in this [Scrollable]
     */
    val totalPages: Int

    /**
     * The last slot/index in which there is a component in this [Scrollable]
     */
    val lastSlot: Int

    /**
     * The total numbers of components a single page of this [Scrollable] can host/display at once.
     */
    val pageSize: Int

    val pageWidth: Int

    val pageHeight: Int
    /**
     * Checks if there is a object in the specified [position]
     */
    fun hasObjectInSlot(position: Int): Boolean

    /**
     * Returns the component in the specified [position], or null if there is none
     */
    operator fun get(position: Int): D?

    /**
     * Places the given [component] in the specified [position] using [place].
     */
    operator fun set(position: Int, component: D)

    /**
     * Places the given [component] in the specified [position].
     *
     * If there is already a component in the [position], an exception will be thrown.
     * It is recommended to use [canPlaceIn] to check for availability before using this function.
     */
    fun place(component: D, position: Int)

    /**
     * Removes any component that might be contained in the specified [position].
     */
    fun remove(position: Int)

    /**
     * Checks if the given [position] is available to contain a new component.
     */
    fun canPlaceIn(position: Int): Boolean

    /**
     * A map that contains all of the components in the current page and it's indexes, sorted.
     */
    val currentPage: SortedMap<Int, D>

    /**
     * Scrolls the [currentIndex] in the specified [direction].
     */
    fun scroll(direction: ScrollDirection)

    /**
     * Sets the [currentIndex] to the specified [page].
     */
    fun goToPage(page: Int)

    /**
     * Returns the index of the provided [drawable].
     *
     * If the [drawable] isn't contained in this
     */
    operator fun get(drawable: D): Int

}