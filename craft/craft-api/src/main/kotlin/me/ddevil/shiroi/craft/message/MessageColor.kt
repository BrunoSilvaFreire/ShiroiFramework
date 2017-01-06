package me.ddevil.shiroi.craft.message

enum class MessageColor {
    PRIMARY,
    SECONDARY,
    NEUTRAL,
    WARNING;

    override fun toString(): String {
        return "$" + (ordinal + 1)
    }
}
