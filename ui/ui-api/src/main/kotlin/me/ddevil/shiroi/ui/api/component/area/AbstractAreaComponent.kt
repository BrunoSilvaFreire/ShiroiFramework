package me.ddevil.shiroi.ui.internal.component.area

import com.google.common.collect.ImmutableSortedSet
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.area.AreaComponent
import me.ddevil.shiroi.ui.internal.component.misc.AbstractComponent
import java.util.*

abstract class AbstractAreaComponent
@JvmOverloads
constructor(
        override var width: Int,
        override var height: Int,
        id: String? = null
) : AbstractComponent(id), AreaComponent {


    override fun redefineArea(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    override val area: SortedSet<UIPosition>
        get() {
            val menuPositionBuilder = ImmutableSortedSet.Builder<UIPosition>(Comparator(UIPosition::compareTo))
            for (x in 0 .. maxX) {
                for (y in 0 .. maxY) {
                    menuPositionBuilder.add(UIPosition(x, y))
                }
            }
            return menuPositionBuilder.build()
        }


    override operator fun contains(position: UIPosition): Boolean {
        return contains(position.x, position.y)
    }

    override fun contains(x: Int, y: Int): Boolean {
        return x < width && y < height
    }

    override val size: Int
        get() = width * height

    override val maxX: Int
        get() = width - 1

    override val maxY: Int
        get() = height - 1


}