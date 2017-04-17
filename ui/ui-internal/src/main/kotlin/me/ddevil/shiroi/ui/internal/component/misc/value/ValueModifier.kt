package me.ddevil.shiroi.ui.internal.component.misc.value

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.event.UIClickEvent


interface ValueModifier<T> {

    var onModifiedListener: OnValueModifiedListener<T>

}

interface OnValueModifiedListener<in T> {
    fun onModified(modificationValue: T, e: UIClickEvent, localPosition: UIPosition)
}
