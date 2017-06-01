package me.ddevil.shiroi.ui.api.misc.handler

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.container.Container

class ContainerHandler<D : Drawable>(container: Container<D>,
                                     onClickAction: ((D?) -> Unit)?) : AbstractHolderHandler<Container<D>, D>(container,
        onClickAction) {

    override fun getPosition(container: Container<D>, component: D): UIPosition {
        return container.getPosition(component) ?: throw IllegalStateException("Couldn't find position for component $component")
    }

    override fun getDrawable(container: Container<D>, position: UIPosition): D {
        return container[position] ?: throw IllegalStateException("There is no component at position $position!")
    }

    override fun hasObjectIn(container: Container<D>, position: UIPosition): Boolean {
        return container.hasObjectIn(position)
    }
}