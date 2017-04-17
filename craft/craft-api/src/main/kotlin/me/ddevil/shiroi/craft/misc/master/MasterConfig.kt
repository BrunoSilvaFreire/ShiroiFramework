package me.ddevil.shiroi.craft.misc.master

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.util.toMap
import me.ddevil.shiroi.util.exception.IllegalValueTypeException
import me.ddevil.shiroi.util.exception.ValueNotFoundException
import me.ddevil.util.Serializable
import org.bukkit.configuration.ConfigurationSection


class MasterConfig : Serializable {
    override fun serialize(): Map<String, Any> = ImmutableMap.builder<String, Any>()
            .put(USE_MASTER_COLOR_IDENTIFIER, useMasterColor)
            .put(MASTER_COLOR_IDENTIFIER, masterColor.serialize())
            .build()

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
        val mobj = map[USE_MASTER_COLOR_IDENTIFIER]
        useMasterColor = (mobj ?: throw ValueNotFoundException(USE_MASTER_COLOR_IDENTIFIER))
                as? Boolean ?: throw IllegalValueTypeException(Boolean::class.java, mobj.javaClass)
        val cobj = map[MASTER_COLOR_IDENTIFIER]
        val colorMap = (cobj ?: throw ValueNotFoundException(MASTER_COLOR_IDENTIFIER))
                as? Map<String, Any> ?: throw IllegalValueTypeException(Boolean::class.java, mobj.javaClass)
        this.masterColor = PluginColorDesign(colorMap)
    }

    constructor(config: ConfigurationSection) : this(config.toMap())
}