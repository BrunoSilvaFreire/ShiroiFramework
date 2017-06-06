package me.ddevil.shiroi.ui.api.component.holder

import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.event.UIClickEvent

interface HolderClickListener<in D : Drawable> {
    fun onClick(drawable: D?, event: UIClickEvent)
}