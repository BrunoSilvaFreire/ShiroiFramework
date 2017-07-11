package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.component.misc.AbstractSlotComponent
import org.bukkit.inventory.ItemStack


abstract class AbstractValueModifier<T> : AbstractSlotComponent<ValueModifierUpdater<T>>, ValueModifier<T> {

    override var value: T
        get() {
            return getter()
        }
        set(value) {
            setter(value)
        }

    val getter: () -> T
    val setter: (T) -> Unit

    @JvmOverloads
    constructor(
            getter: () -> T,
            setter: (T) -> Unit,
            updater: ValueModifierUpdater<T>,
            initialIcon: ItemStack,
            id: String? = null
    ) : super(updater, initialIcon, id) {
        this.getter = getter
        this.setter = setter
    }

    constructor(getter: () -> T, setter: (T) -> Unit, icon: ItemStack, id: String?) : super(icon, id) {
        this.getter = getter
        this.setter = setter
    }

    override fun updateIcon(updater: ValueModifierUpdater<T>, oldItem: ItemStack) = updater.update(oldItem, value)

}

