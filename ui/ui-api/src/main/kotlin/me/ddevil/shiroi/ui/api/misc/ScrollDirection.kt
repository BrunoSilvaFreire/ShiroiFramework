package me.ddevil.shiroi.ui.api.misc

/**
 * Represents a direction in which a [me.ddevil.shiroi.ui.api.component.scrollable.Scrollable] can be scrolled.
 */
enum class ScrollDirection constructor(val scrollQuantity: Int) {
    NEXT(1),
    PREVIOUS(-1);
}
