package me.ddevil.shiroi.ui.api.component.scrollable

import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.holder.Holder
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import java.util.*

interface Scrollable<D : Drawable> : Holder<D> {
    val componentMap: Map<Int, D>

    var currentIndex: Int

    val totalPages: Int

    val lastSlot: Int
    val pageSize: Int

    fun hasObjectInSlot(position: Int): Boolean

    operator fun get(position: Int): D?

    fun place(drawable: D, position: Int)

    fun remove(position: Int)

    fun canPlaceIn(position: Int): Boolean

    val currentPage: SortedMap<Int, D>

    fun scroll(direction: ScrollDirection)

    fun goToPage(page: Int)

    operator fun get(drawable: D): Int

}