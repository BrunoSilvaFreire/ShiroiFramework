package me.ddevil.shiroi.craft.misc.master

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.util.toMap
import me.ddevil.util.*
import org.bukkit.configuration.ConfigurationSection


class MasterConfig : Serializable {
    override fun serialize(): Map<String, Any> = immutableMap {
        this[USE_MASTER_COLOR_IDENTIFIER] = useMasterColor
        this[MASTER_COLOR_IDENTIFIER] = masterColor.serialize()
    }

    companion object {
        const val USE_MASTER_COLOR_IDENTIFIER = "useMasterColor"
        const val MASTER_COLOR_IDENTIFIER = "masterColor"

    }

    val useMasterColor: Boolean
    val masterColor: PluginColorDesign

    @JvmOverloads
    constructor(useMasterColor: Boolean = false,
                masterColor: PluginColorDesign = PluginColorDesign.SHIROI_COLOR_DESIGN) {
        this.useMasterColor = useMasterColor
        this.masterColor = masterColor
    }

    constructor(map: Map<String, Any>) {
        useMasterColor = map.getBoolean(USE_MASTER_COLOR_IDENTIFIER)
        val colorMap = map.getMapAny(MASTER_COLOR_IDENTIFIER)
        this.masterColor = PluginColorDesign(colorMap)
    }

    constructor(config: ConfigurationSection) : this(config.toMap())
}