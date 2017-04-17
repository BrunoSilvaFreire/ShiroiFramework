package me.ddevil.shiroi.ui.internal.component.misc.value

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import me.ddevil.shiroi.ui.internal.component.misc.ClickableItemSlotComponent
import org.bukkit.inventory.ItemStack

class ValueModifierItem<T> : ClickableItemSlotComponent {

    constructor(updater: ItemUpdater,
                defaultIcon: ItemStack,
                id: String?,
                value: T,
                modifier: ValueModifier<T>)
            : super(updater, defaultIcon, id, ValueModifierItemAction(modifier, value))

    constructor(icon: ItemStack,
                id: String?,
                value: T,
                modifier: ValueModifier<T>) : super(icon, id, ValueModifierItemAction(modifier, value))
}

class ValueModifierItemAction<T>(
        val modifier: ValueModifier<T>,
        val value: T
) : Action {

    override fun invoke(e: UIClickEvent, localPosition: UIPosition) {
        modifier.onModifiedListener.onModified(value, e, localPosition)
    }

}