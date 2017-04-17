package me.ddevil.shiroi.craft.config.loader

import org.bukkit.Material

class BukkitMaterialLoader : AbstractConfigLoader<String, Material>(String::class.java, Material::class.java) {
    override fun load(type: String) = Material.matchMaterial(type) ?: throw IllegalArgumentException("Unknown material $type")
}

