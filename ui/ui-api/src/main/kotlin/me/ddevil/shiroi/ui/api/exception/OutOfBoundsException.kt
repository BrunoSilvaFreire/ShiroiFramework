package me.ddevil.shiroi.ui.api.exception

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.holder.Holder

class OutOfBoundsException : IllegalStateException {
    private val menu: Holder<*>
    private val position: UIPosition

    constructor (menu: Holder<*>, position: UIPosition) : this(menu, position, false)

    constructor(menu: Holder<*>,
                position: UIPosition,
                child: Boolean) : super((if (child) "Child " else "") + "Position " + position + " is out of bounds of container " + menu + "!") {
        this.menu = menu
        this.position = position
    }

    fun getMenu(): Holder<*> {
        return menu
    }

    fun getPosition(): UIPosition {
        return position
    }
}