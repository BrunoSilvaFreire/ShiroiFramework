package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import org.bukkit.inventory.ItemStack

open class AddingValueModifier<T> : AbstractValueModifier<T> {

    inner class AdderAction : Action {
        override fun invoke(p1: UIClickEvent, p2: UIPosition) {
            value = sumFunction(value, valueToAdd)
        }
    }

    override val action: Action = AdderAction()

    val sumFunction: (T, T) -> (T)
    var valueToAdd: T

    @JvmOverloads
    constructor(getter: () -> T, setter: (T) -> Unit, sumFunction: (T, T) -> T, valueToAdd: T, updater: ValueModifierUpdater<T>, initialIcon: ItemStack, id: String? = null) : super(getter, setter, updater, initialIcon, id) {
        this.sumFunction = sumFunction
        this.valueToAdd = valueToAdd
    }

    @JvmOverloads
    constructor(getter: () -> T, setter: (T) -> Unit, sumFunction: (T, T) -> T, valueToAdd: T, icon: ItemStack, id: String? = null) : super(getter, setter, icon, id) {
        this.sumFunction = sumFunction
        this.valueToAdd = valueToAdd
    }
}
