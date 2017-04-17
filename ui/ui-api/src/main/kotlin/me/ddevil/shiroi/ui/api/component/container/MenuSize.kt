package me.ddevil.shiroi.ui.api.component.container

enum class MenuSize {
    ONE_ROW,
    TWO_ROWS,
    THREE_ROWS,
    FOUR_ROWS,
    FIVE_ROWS,
    SIX_ROWS;

    val rows: Int
        get() = ordinal + 1
}