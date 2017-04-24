package me.ddevil.shiroi.ui.internal.component.container

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import org.bukkit.inventory.ItemStack
import java.util.*

open class ArrayContainer<H : Drawable>
@JvmOverloads
constructor(
        expectedType: Class<H>,
        width: Int,
        height: Int,
        background: ItemStack? = null,
        id: String? = null
) : AbstractContainer<H>(expectedType, width, height, background, id) {

    private val map = Array(width) { arrayOfNulls<Drawable>(height) }

    override fun remove0(position: UIPosition) {
        remove0(position.x, position.y)
    }

    override fun remove0(x: Int, y: Int) {
        map[x][y] = null
    }

    override fun place0(drawable: H, position: UIPosition) {
        place0(drawable, position.x, position.y)
    }

    override fun place0(drawable: H, x: Int, y: Int) {
        map[x][y] = drawable
    }

    override fun hasObjectIn(position: UIPosition) = get(position) != null

    override fun hasObjectIn(x: Int, y: Int) = get(x, y) != null

    override fun get(position: UIPosition): H? {
        return get(position.x, position.y)
    }

    override fun get(x: Int, y: Int): H? {
        val drawable = map[x][y] ?: return null
        return drawable as? H ?: throw IllegalStateException("Illegal object $drawable!")
    }

    override fun getMenuMap(): SortedMap<UIPosition, H> {
        val map = TreeMap<UIPosition, H>()
        for (x in 0 .. width - 1) {
            for (y in 0 .. height - 1) {
                val h = get(x, y) ?: continue
                map[UIPosition(x, y)] = h
            }
        }
        return map
    }

    override fun getPosition(drawable: H): UIPosition? {
        for (x in 0 .. width - 1) {
            for (y in 0 .. height - 1) {
                val h = get(x, y) ?: continue
                if (h == drawable) {
                    return UIPosition(x, y)
                }
            }
        }
        return null
    }

    override fun clear() {
        for (x in 0 .. width - 1) {
            for (y in 0 .. height - 1) {
                remove(x, y)
            }
        }
    }

    override val components: List<H>
        get() {
            val map = mutableListOf<H>()
            for (x in 0 .. width - 1) {
                for (y in 0 .. height - 1) {
                    map.add(get(x, y) ?: continue)
                }
            }
            return map
        }
}
