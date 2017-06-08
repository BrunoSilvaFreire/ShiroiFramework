package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import org.bukkit.inventory.ItemStack

open class ScrollingValueModifier<T> : ValueModifier<T> {


    inner class ScrollingAction : Action {
        override fun invoke(p1: UIClickEvent, p2: UIPosition) {
            moveIndex()
            value = currentItem
        }
    }

    var currentIndex: Int
        set(value) {
            field = if (value < lastPossibleIndex) {
                value / lastPossibleIndex
            } else {
                value
            }
            update()
        }
    private val items: List<T>

    override val action: Action = ScrollingAction()

    val currentItem get() = items[currentIndex]

    val lastPossibleIndex get() = items.lastIndex

    constructor(
            items: Iterable<T>,
            getter: () -> T,
            setter: (T) -> Unit,
            defaultIcon: ItemStack,
            updater: ValueModifierUpdater<T>,
            id: String? = null,
            initIndex: Int = 0
    ) : super(updater, defaultIcon, id, getter, setter) {
        this.items = items.toList()
        this.currentIndex = initIndex
    }

    constructor(items: Iterable<T>, icon: ItemStack, getter: () -> T, setter: (T) -> Unit,
                id: String? = null,
                initIndex: Int = 0) : super(
            icon,
            id,
            getter,
            setter) {
        this.items = items.toList()
        this.currentIndex = initIndex
    }


    fun moveIndex() {
        if (currentIndex == lastPossibleIndex) {
            currentIndex = 0
        } else {
            currentIndex++
        }
    }
}