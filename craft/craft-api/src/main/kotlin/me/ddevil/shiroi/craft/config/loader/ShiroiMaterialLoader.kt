package me.ddevil.shiroi.craft.config.loader

import me.ddevil.shiroi.util.misc.item.Material

class ShiroiMaterialLoader : AbstractConfigLoader<String, Material>(String::class.java, Material::class.java) {
    override fun load(type: String): Material {
        return Material.matchMaterial(type) ?: throw IllegalArgumentException("Unknown material $type")
    }

}