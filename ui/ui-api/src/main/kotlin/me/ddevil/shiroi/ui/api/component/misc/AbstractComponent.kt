package me.ddevil.shiroi.ui.api.component.misc

import me.ddevil.shiroi.ui.api.component.Component

abstract class AbstractComponent
@JvmOverloads
constructor(
        override var id: String? = null
) : Component