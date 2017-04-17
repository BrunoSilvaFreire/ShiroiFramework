package me.ddevil.shiroi.ui.api.exception

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Component

class PositionAlreadyOccupiedException
constructor(
        val position: UIPosition,
        val found: Component
) : IllegalStateException("There is already a different object in position $position ($found)!")