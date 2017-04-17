package me.ddevil.shiroi.util.misc.design

import com.google.common.collect.ImmutableMap
import me.ddevil.util.getOrException
import me.ddevil.util.serialization.Serializable

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
    }

    constructor(primaryColor: String, secondaryColor: String) : this(
            MinecraftColor.getByString(primaryColor),
            MinecraftColor.getByString(secondaryColor)
    )

    constructor(map: Map<String, Any>) : this(
            map.getOrException<String>(PRIMARY_COLOR_IDENTIFIER),
            map.getOrException<String>(SECONDARY_COLOR_IDENTIFIER)
    )

    override fun serialize(): Map<String, Any> = ImmutableMap.Builder<String, Any>()
            .put(PRIMARY_COLOR_IDENTIFIER, primaryColor.name)
            .put(SECONDARY_COLOR_IDENTIFIER, secondaryColor.name)
            .build()

    override fun toString() = "ColorDesign(primaryColor=$primaryColor, secondaryColor=$secondaryColor)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ColorDesign
        when {
            primaryColor != other.primaryColor -> return false
            secondaryColor != other.secondaryColor -> return false
            else -> return true
        }

    }

    override fun hashCode(): Int {
        var result = primaryColor.hashCode()
        result = 31 * result + secondaryColor.hashCode()
        return result
    }


}

