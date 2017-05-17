package me.ddevil.shiroi.example.config

import me.ddevil.shiroi.craft.config.FileConfigValue
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.util.createConfig
import me.ddevil.shiroi.util.misc.design.MinecraftColor

class GenericConfigValue<out T : Any>(override val source: GenericConfigSource,
                                      override val path: String,
                                      override val defaultValue: T) : FileConfigValue<T, GenericConfigSource> {
    companion object {
        val COLOR_DESIGN = GenericConfigValue(GenericConfigSource.MAIN_CONFIG,
                "plugin.colorDesign",
                createConfig(PluginColorDesign(MinecraftColor.AQUA, MinecraftColor.DARK_AQUA).serialize()))
        val PLUGIN_PREFIX = GenericConfigValue(GenericConfigSource.MESSAGES_CONFIG,
                "plugin.prefix",
                "$1Generic$2Plugin")
        val MESSAGE_SEPARATOR = GenericConfigValue(GenericConfigSource.MESSAGES_CONFIG,
                "plugin.messageSeparator",
                " &6&l> $3")
    }

}