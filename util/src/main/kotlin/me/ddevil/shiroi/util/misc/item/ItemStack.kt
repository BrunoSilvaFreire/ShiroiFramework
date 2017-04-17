package me.ddevil.shiroi.util.misc.item

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.util.DEFAULT_SHIROI_ITEM_AMOUNT_IDENTIFIER
import me.ddevil.util.getOrException

class ItemStack : Item {
    var quantity: Int

    @JvmOverloads
    constructor(material: Material, data: Byte = 0, quantity: Int = 0) : super(material, data) {
        this.quantity = quantity
    }

    constructor(map: Map<String, Any>) : super(map) {
        this.quantity = if (map.containsKey(DEFAULT_SHIROI_ITEM_AMOUNT_IDENTIFIER)) {
            map.getOrException<Number>(DEFAULT_SHIROI_ITEM_AMOUNT_IDENTIFIER).toInt()
        } else {
            1
        }
    }

    override fun serialize(): Map<String, Any> {
        val map = ImmutableMap.builder<String, Any>().putAll(super.serialize())
        if (quantity != 1) {
            map.put(DEFAULT_SHIROI_ITEM_AMOUNT_IDENTIFIER, quantity)
        }
        return map.build()
    }
}