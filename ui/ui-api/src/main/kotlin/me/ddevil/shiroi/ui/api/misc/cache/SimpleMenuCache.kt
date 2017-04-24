package me.ddevil.shiroi.ui.internal.misc.cache

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import me.ddevil.shiroi.ui.api.component.container.Menu
import me.ddevil.shiroi.ui.api.misc.cache.MenuLoader
import java.util.*

class SimpleMenuCache
@JvmOverloads
constructor(internalLoaders: MutableSet<MenuLoader<*>> = mutableSetOf()) : AbstractMenuCache(internalLoaders) {
    private var cache: Multimap<UUID, Menu<*>> = HashMultimap.create()
    override fun <T : Menu<*>> get(uuid: UUID, type: Class<T>): T {
        val cached = cache[uuid]
        if (cached.any { it.javaClass.isAssignableFrom(type) }) {
            return cached.first { it.javaClass.isAssignableFrom(type) } as T
        }
        val loader = findLoaderFor(type) ?: throw IllegalStateException("There is no menu loader for class ${type.name}")
        val menu = loader.load()
        cache.put(uuid, menu)
        return menu
    }


}