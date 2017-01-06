package me.ddevil.shiroi.craft.misc.design

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.util.misc.design.ColorDesign
import me.ddevil.shiroi.util.misc.design.MinecraftColor

class PluginColorDesign : ColorDesign {
    companion object {
        const val NEUTRAL_COLOR_IDENTIFIER = "neutralColor"
        const val WARNING_COLOR_IDENTIFIER = "warningColor"
        val SHIROI_COLOR_DESIGN = PluginColorDesign(MinecraftColor.AQUA, MinecraftColor.DARK_AQUA, MinecraftColor.GRAY, MinecraftColor.RED)

    }

    val neutralColor: MinecraftColor
    val warningColor: MinecraftColor

    constructor(primaryColor: MinecraftColor, secondaryColor: MinecraftColor, neutralColor: MinecraftColor, warningColor: MinecraftColor) : super(primaryColor, secondaryColor) {
        this.neutralColor = neutralColor
        this.warningColor = warningColor
    }

    constructor(primaryColor: String, secondaryColor: String, neutralColor: String, warningColor: String) : super(primaryColor, secondaryColor) {
        this.neutralColor = MinecraftColor.getByChar(neutralColor)
        this.warningColor = MinecraftColor.getByChar(warningColor)

    }

    constructor(map: Map<String, Any>) : super(map) {
        this.neutralColor = MinecraftColor.getByChar(map[NEUTRAL_COLOR_IDENTIFIER].toString())
        this.warningColor = MinecraftColor.getByChar(map[WARNING_COLOR_IDENTIFIER].toString())
    }

    override fun serialize(): Map<String, Any> = ImmutableMap.Builder<String, Any>()
            .putAll(super.serialize())
            .put(NEUTRAL_COLOR_IDENTIFIER, neutralColor.name)
            .put(WARNING_COLOR_IDENTIFIER, warningColor.name)
            .build()
}