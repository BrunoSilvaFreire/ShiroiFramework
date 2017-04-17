package me.ddevil.shiroi.ui.api.component

import me.ddevil.shiroi.ui.api.misc.Action

/**
 * Not yet done, ignore
 */
interface MultiActionClickable : Clickable {

    val subActions: Set<Action>

    fun addSubAction(action: Action)

    fun removeSubAction(action: Action)

}