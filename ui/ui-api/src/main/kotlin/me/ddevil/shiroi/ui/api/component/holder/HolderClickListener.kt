package me.ddevil.shiroi.ui.api.component.holder

import me.ddevil.shiroi.ui.api.component.Drawable

interface HolderClickListener<in D : Drawable> {
    fun onClick(drawable: D)
}