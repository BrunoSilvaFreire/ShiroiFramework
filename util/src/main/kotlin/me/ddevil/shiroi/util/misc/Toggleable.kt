package me.ddevil.shiroi.util.misc

interface Toggleable {
    fun <T : Toggleable> T.enable(): T

    fun <T : Toggleable> T.disable(): T
}
