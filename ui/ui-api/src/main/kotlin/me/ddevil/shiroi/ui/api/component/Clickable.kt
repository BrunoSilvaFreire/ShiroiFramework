package me.ddevil.shiroi.ui.api.component

import me.ddevil.shiroi.ui.api.misc.Action

/**
 * A component that executes an [Action] when clicked. Differently from [Component.update], the action is only executed when the
 * component is clicked, and not when updated, so use this to execute actions on click, and not on update.
 */
interface Clickable : Component {
    /**
     * The action to be executed when the component clicked.
     */
    val action: Action
}

