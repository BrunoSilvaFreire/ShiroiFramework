package me.ddevil.shiroi.craft.message

enum class MessageColor {
    PRIMARY,
    SECONDARY,
    NEUTRAL,
    WARNING,
    SUCCESS;

    override fun toString() = "$${ordinal + 1}"
}
