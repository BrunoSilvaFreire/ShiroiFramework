package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.misc.ItemSlotComponent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import org.bukkit.inventory.ItemStack
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class ValueModifier<T> : ItemSlotComponent, Clickable {
    override val action: Action = createAction()

    private fun createAction(): Action {
        return object : Action {
            override fun invoke(p1: UIClickEvent, p2: UIPosition) {
            }
        }
    }

    private var value: T by ModifierDelegate()
    private val getter: () -> T
    private val setter: (T) -> Unit

    constructor(updater: ItemUpdater,
                defaultIcon: ItemStack,
                id: String?,
                getter: () -> T,
                setter: (T) -> Unit) : super(updater, defaultIcon, id) {
        this.getter = getter
        this.setter = setter
    }

    constructor(icon: ItemStack, id: String?, getter: () -> T, setter: (T) -> Unit) : super(icon, id) {
        this.getter = getter
        this.setter = setter
    }


    inner class ModifierDelegate : ReadWriteProperty<ValueModifier<T>, T> {
        override fun getValue(thisRef: ValueModifier<T>, property: KProperty<*>) = getter.invoke()

        override fun setValue(thisRef: ValueModifier<T>, property: KProperty<*>, value: T) = setter(value)
    }
}