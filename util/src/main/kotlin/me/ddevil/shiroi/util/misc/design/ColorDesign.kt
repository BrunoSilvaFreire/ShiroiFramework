package me.ddevil.shiroi.util.misc.design

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.util.exception.IllegalColorException
import me.ddevil.shiroi.util.serialization.Serializable

open class ColorDesign : Serializable {

    companion object {
        const val PRIMARY_COLOR_IDENTIFIER = "primaryColor"
        const val SECONDARY_COLOR_IDENTIFIER = "secondaryColor"
    }

    val primaryColor: MinecraftColor
    val secondaryColor: MinecraftColor

    constructor(primaryColor: MinecraftColor, secondaryColor: MinecraftColor) {
        this.primaryColor = primaryColor
        this.secondaryColor = secondaryColor
        if (!primaryColor.isColor) {
            throw IllegalColorException(primaryColor)
        }
        if (!secondaryColor.isColor) {
            throw IllegalColorException(secondaryColor)
        }
    }

    constructor(ps: String, ss: String) : this(
            primaryColor = MinecraftColor.getByChar(ps),
            secondaryColor = MinecraftColor.getByChar(ss)
    )

    constructor(map: Map<String, Any>) : this(map[PRIMARY_COLOR_IDENTIFIER].toString(), map[SECONDARY_COLOR_IDENTIFIER].toString())

    override fun serialize(): Map<String, Any> = ImmutableMap.Builder<String, Any>()
            .put(PRIMARY_COLOR_IDENTIFIER, primaryColor.name)
            .put(SECONDARY_COLOR_IDENTIFIER, secondaryColor.name)
            .build()

}

