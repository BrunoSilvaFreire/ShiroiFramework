package me.ddevil.shiroi.ui.api.misc.cache

import me.ddevil.shiroi.ui.api.component.container.Menu
import java.util.*

/**
 * A menu cache holds a instance of a menu for each player. Useful for menus that are displayed differently for each player.
 */
interface MenuCache {

    operator fun <T : Menu<*>> get(uuid: UUID, type: Class<T>): T

    fun registerLoader(loader: MenuLoader<*>)

    fun removeLoader(loader: MenuLoader<*>)

    val loaders: Set<MenuLoader<*>>

    fun <T : Menu<*>> findLoaderFor(type: Class<T>): MenuLoader<T>?
}

