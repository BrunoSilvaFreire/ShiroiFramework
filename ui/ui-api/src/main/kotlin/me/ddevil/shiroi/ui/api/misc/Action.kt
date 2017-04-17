package me.ddevil.shiroi.ui.api.misc

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.event.UIClickEvent

/**
 * Pretty explanatory, an action is executed everytime it's [me.ddevil.shiroi.ui.api.component.Clickable]
 * object is... you guessed it, clicked.
 */
interface Action : ((UIClickEvent), (UIPosition)) -> (Unit)

