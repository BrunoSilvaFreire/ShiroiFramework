package me.ddevil.shiroi.ui.api.event

enum class InteractionType(val clickDirection: ClickDirection) {
    INVENTORY_CLICK_RIGHT(ClickDirection.RIGHT),
    INVENTORY_CLICK_LEFT(ClickDirection.LEFT),
    INTERACT_CLICK_RIGHT(ClickDirection.RIGHT),
    INTERACT_CLICK_LEFT(ClickDirection.LEFT);

    val isRightClick = clickDirection == ClickDirection.RIGHT

    val isLeftClick = clickDirection == ClickDirection.LEFT

    enum class ClickDirection {
        LEFT,
        RIGHT
    }
}
