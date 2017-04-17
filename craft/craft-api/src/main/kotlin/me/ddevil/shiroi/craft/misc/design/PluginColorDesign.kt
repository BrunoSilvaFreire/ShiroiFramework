package me.ddevil.shiroi.craft.misc.design

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.util.misc.design.ColorDesign
import me.ddevil.shiroi.util.misc.design.MinecraftColor
import org.bukkit.configuration.serialization.ConfigurationSerializable

class PluginColorDesign : ColorDesign, ConfigurationSerializable {
    companion object {
        const val NEUTRAL_COLOR_IDENTIFIER = "neutralColor"
        const val WARNING_COLOR_IDENTIFIER = "warningColor"
        const val SUCCESS_COLOR_IDENTIFIER = "successColor"
        val SHIROI_COLOR_DESIGN = PluginColorDesign(MinecraftColor.AQUA, MinecraftColor.DARK_AQUA)

    }

    val neutralColor: MinecraftColor
    val warningColor: MinecraftColor
    val successColor: MinecraftColor

    @JvmOverloads
    constructor(primaryColor: MinecraftColor,
                secondaryColor: MinecraftColor,
                neutralColor: MinecraftColor = MinecraftColor.GRAY,
                warningColor: MinecraftColor = MinecraftColor.RED,
                successColor: MinecraftColor = MinecraftColor.GREEN) : super(primaryColor, secondaryColor) {
        this.neutralColor = neutralColor
        this.warningColor = warningColor
        this.successColor = successColor
    }

    constructor(primaryColor: String,
                secondaryColor: String,
                neutralColor: String,
                warningColor: String,
                successColor: String) : super(primaryColor, secondaryColor) {
        this.neutralColor = MinecraftColor.getByString(neutralColor)
        this.warningColor = MinecraftColor.getByString(warningColor)
        this.successColor = MinecraftColor.getByString(successColor)

    }

    constructor(map: Map<String, Any>) : super(map) {
        this.neutralColor = MinecraftColor.getByString(map[NEUTRAL_COLOR_IDENTIFIER].toString())
        this.warningColor = MinecraftColor.getByString(map[WARNING_COLOR_IDENTIFIER].toString())
        this.successColor = MinecraftColor.getByString(map[SUCCESS_COLOR_IDENTIFIER].toString())
    }

    override fun serialize(): Map<String, Any> = ImmutableMap.Builder<String, Any>()
            .putAll(super.serialize())
            .put(NEUTRAL_COLOR_IDENTIFIER, neutralColor.name)
            .put(WARNING_COLOR_IDENTIFIER, warningColor.name)
            .put(SUCCESS_COLOR_IDENTIFIER, successColor.name)
            .build()

    override fun toString() = "PluginColorDesign(primaryColor=$primaryColor, secondaryColor=$secondaryColor, neutralColor=$neutralColor, warningColor=$warningColor, successColor=$successColor)"

}