package me.ddevil.shiroi.ui.internal.component.misc

import me.ddevil.shiroi.ui.api.component.Component

abstract class AbstractComponent
@JvmOverloads
constructor(
        override var id: String? = null
) : Component