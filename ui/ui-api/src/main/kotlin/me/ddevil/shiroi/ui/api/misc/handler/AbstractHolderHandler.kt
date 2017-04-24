package me.ddevil.shiroi.ui.internal.misc.handler

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.Drawable
import me.ddevil.shiroi.ui.api.component.area.AreaComponent
import me.ddevil.shiroi.ui.api.component.holder.Holder
import me.ddevil.shiroi.ui.api.event.UIActionEvent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action

abstract class AbstractHolderHandler<in H : Holder<D>, D : Drawable>(private val container: H,
                                                                     private val onClickAction: ((D) -> Unit)?) : Action {

    override fun invoke(e: UIClickEvent, localPosition: UIPosition) {
        if (hasObjectIn(container, localPosition)) {
            val drawable = getDrawable(container, localPosition)
            drawable.update()
            if (drawable is Clickable) {
                val c = drawable
                val objLocalPos: UIPosition
                if (drawable is AreaComponent) {
                    objLocalPos = localPosition - getPosition(container, drawable)
                } else {
                    objLocalPos = UIPosition.ZERO
                }
                val action = c.action
                action.invoke(e, objLocalPos)
                UIActionEvent(c, e.clickedSlot, e.player, e.clickType, e.placedBlock).call()
            }
            val subAction = onClickAction
            if (subAction != null) {
                subAction(drawable)
            }
        }
    }

    protected abstract fun getPosition(container: H, `object`: D): UIPosition

    protected abstract fun getDrawable(container: H, position: UIPosition): D

    protected abstract fun hasObjectIn(container: H, position: UIPosition): Boolean
}

