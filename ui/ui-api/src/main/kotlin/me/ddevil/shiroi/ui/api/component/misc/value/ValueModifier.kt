package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.misc.AbstractSlotComponent
import org.bukkit.inventory.ItemStack
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


abstract class ValueModifier<T> : AbstractSlotComponent<ValueModifierUpdater<T>>, Clickable {

    protected var value: T by ModifierDelegate()
    private val getter: () -> T
    private val setter: (T) -> Unit

    constructor(updater: ValueModifierUpdater<T>,
                initialIcon: ItemStack,
                id: String?,
                getter: () -> T,
                setter: (T) -> Unit) : super(updater, initialIcon, id) {
        this.getter = getter
        this.setter = setter
    }

    constructor(icon: ItemStack, id: String?, getter: () -> T, setter: (T) -> Unit) : super(icon, id) {
        this.getter = getter
        this.setter = setter
    }

    override fun updateIcon(updater: ValueModifierUpdater<T>, oldItem: ItemStack) = updater.update(oldItem, value)

    inner class ModifierDelegate : ReadWriteProperty<ValueModifier<T>, T> {
        override fun getValue(thisRef: ValueModifier<T>, property: KProperty<*>) = getter.invoke()

        override fun setValue(thisRef: ValueModifier<T>, property: KProperty<*>, value: T) = setter(value)
    }
}

