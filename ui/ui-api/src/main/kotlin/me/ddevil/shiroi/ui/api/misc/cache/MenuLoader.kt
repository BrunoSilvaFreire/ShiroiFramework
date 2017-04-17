package me.ddevil.shiroi.ui.api.misc.cache

import me.ddevil.shiroi.ui.api.component.container.Menu

interface MenuLoader<out T : Menu<*>> {
    fun <T : Menu<*>> supports(type: Class<T>): Boolean
    fun load(): T
}