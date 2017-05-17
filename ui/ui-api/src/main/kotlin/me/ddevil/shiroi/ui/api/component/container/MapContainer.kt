package me.ddevil.shiroi.ui.api.component.container

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import org.bukkit.inventory.ItemStack
import java.util.*

open class MapContainer<H : Drawable>
@JvmOverloads
constructor(
        expectedType: Class<H>,
        width: Int,
        height: Int,
        background: ItemStack? = null,
        id: String? = null
) : AbstractContainer<H>(expectedType, width, height, background, id) {
    private val componentMap: MutableMap<UIPosition, H> = HashMap()

    override fun remove0(position: UIPosition) {
        componentMap.remove(position)
    }

    override fun remove0(x: Int, y: Int) {
        remove0(UIPosition(x, y))
    }

    override fun place0(drawable: H, position: UIPosition) {
        componentMap[position] = drawable
    }

    override fun place0(drawable: H, x: Int, y: Int) {
        place0(drawable, UIPosition(x, y))
    }

    override fun clear() {
        componentMap.clear()
    }

    override fun hasObjectIn(position: UIPosition) = componentMap.containsKey(position)

    override fun hasObjectIn(x: Int, y: Int) = hasObjectIn(UIPosition(x, y))
    override fun get(position: UIPosition) = componentMap[position]

    override fun get(x: Int, y: Int) = get(UIPosition(x, y))
    override fun getMenuMap() = TreeMap(componentMap)
    override fun getPosition(drawable: H): UIPosition? {
        if (componentMap.containsValue(drawable)) {
            return componentMap.filter { get(it.key) == drawable }.minBy { it.key.toInvSlot() }?.key
        }
        return null
    }

    override val components: List<H>
        get() = componentMap.values.toList()
}