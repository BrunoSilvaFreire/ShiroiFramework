package me.ddevil.shiroi.ui.api.misc.cache

import me.ddevil.shiroi.ui.api.component.container.Menu

abstract class AbstractMenuCache
@JvmOverloads
constructor(
        val internalLoaders: MutableSet<MenuLoader<*>> = mutableSetOf()
) : MenuCache {

    override fun registerLoader(loader: MenuLoader<*>) {
        internalLoaders += loader
    }

    override fun removeLoader(loader: MenuLoader<*>) {
        internalLoaders.remove(loader)
    }

    override val loaders: Set<MenuLoader<*>>
        get() = setOf(*internalLoaders.toTypedArray())

    override fun <T : Menu<*>> findLoaderFor(type: Class<T>): MenuLoader<T>? {
        return internalLoaders.firstOrNull { it.supports(type) } as? MenuLoader<T>?
    }

}