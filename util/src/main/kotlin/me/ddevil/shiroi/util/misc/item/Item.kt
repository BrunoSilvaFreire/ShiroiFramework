package me.ddevil.shiroi.util.misc.item

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.util.DEFAULT_SHIROI_ITEM_DATA_IDENTIFIER
import me.ddevil.shiroi.util.DEFAULT_SHIROI_ITEM_TYPE_IDENTIFIER
import me.ddevil.shiroi.util.exception.item.IncompatibleEnchantmentException
import me.ddevil.shiroi.util.exception.item.SimilarEnchantmentException
import me.ddevil.shiroi.util.exception.material.UnknownMaterialException
import me.ddevil.shiroi.util.misc.item.enchantment.Enchantment
import me.ddevil.util.Serializable
import me.ddevil.util.getOrException

open class Item : Serializable {
    var material: Material
    var data: Byte
    private val internalEnchantments: MutableSet<Enchantment>

    val enchantments: Set<Enchantment> get() = setOf(*internalEnchantments.toTypedArray())

    @JvmOverloads
    constructor(material: Material, data: Byte = 0) {
        this.material = material
        this.data = data
        this.internalEnchantments = HashSet()
    }

    constructor(map: Map<String, Any>) {
        val materialName = map.getOrException<String>(DEFAULT_SHIROI_ITEM_TYPE_IDENTIFIER)
        this.material = Material.matchMaterial(materialName) ?: throw UnknownMaterialException(materialName)
        this.data = if (map.containsKey(DEFAULT_SHIROI_ITEM_DATA_IDENTIFIER)) {
            map.getOrException<Number>(DEFAULT_SHIROI_ITEM_DATA_IDENTIFIER).toByte()
        } else {
            0
        }
        this.internalEnchantments = HashSet()
        //todo, enchantment loading
    }

    override fun serialize(): Map<String, Any> {
        val map = ImmutableMap.builder<String, Any>()
                .put(DEFAULT_SHIROI_ITEM_TYPE_IDENTIFIER, material.name)
        if (data > 0) {
            map.put(DEFAULT_SHIROI_ITEM_DATA_IDENTIFIER, data)
        }
        return map.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Item || material != other.material) {
            return false
        }
        return data == other.data
    }

    override fun hashCode(): Int {
        var result = material.hashCode()
        result = 31 * result + data
        return result
    }

    fun addEnchantmentMax(enchantment: Enchantment) {
        addEnchantment(enchantment, enchantment.maxLevel)
    }

    @JvmOverloads
    fun addEnchantment(enchantment: Enchantment, level: Int = enchantment.startLevel) {
        if (hasAnyIncompatibleEnchantment(enchantment)) {
            throw IncompatibleEnchantmentException(enchantment)
        }
        if (hasSimilarEnchantments(enchantment)) {
            throw SimilarEnchantmentException(enchantment)
        }
        if (level > enchantment.maxLevel || level < enchantment.startLevel) {
            throw IllegalArgumentException("The provided $level is out of this enchantment's range! (${enchantment.startLevel}-${enchantment.maxLevel})")
        }
        internalEnchantments.add(enchantment)
    }

    fun removeEnchantment(enchantment: Enchantment) {
        internalEnchantments.removeAll {
            it == enchantment
        }
    }

    fun hasSimilarEnchantments(enchantment: Enchantment) = internalEnchantments.any {
        it == enchantment
    }

    fun hasAnyIncompatibleEnchantment(enchantment: Enchantment) = internalEnchantments.any {
        it.conflictsWith(enchantment)
    }

    override fun toString() = "${material.name}:$data"
}

