package me.ddevil.shiroi.craft.util

import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.util.DEFAULT_SHIROI_ITEM_AMOUNT_IDENTIFIER
import me.ddevil.shiroi.util.DEFAULT_SHIROI_ITEM_DATA_IDENTIFIER
import me.ddevil.shiroi.util.DEFAULT_SHIROI_ITEM_LORE_IDENTIFIER
import me.ddevil.shiroi.util.DEFAULT_SHIROI_MATERIAL_TYPE_IDENTIFIER
import me.ddevil.util.DEFAULT_NAME_IDENTIFIER
import me.ddevil.util.getOrElse
import me.ddevil.util.getOrException
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

fun parseConfig(config: ConfigurationSection, messageManager: MessageManager): ItemStack {
    return parseMap(config.toMap(), messageManager)
}

fun parseMap(map: Map<String, Any>, messageManager: MessageManager): ItemStack {
    if (!map.containsKey(DEFAULT_SHIROI_MATERIAL_TYPE_IDENTIFIER)) {
        throw IllegalArgumentException("Map ($map) does not contain an type for the item!")
    }
    val sType = map[DEFAULT_SHIROI_MATERIAL_TYPE_IDENTIFIER]
    val type: Material
    if (sType is Number) {
        type = Material.getMaterial(sType.toInt())
    } else {
        type = Material.getMaterial(sType.toString())
    }
    val amount: Int = map.getOrElse(DEFAULT_SHIROI_ITEM_AMOUNT_IDENTIFIER, 1)
    val data: Short = map.getOrElse(DEFAULT_SHIROI_ITEM_DATA_IDENTIFIER, 0).toShort()
    val builder = ShiroiItemBuilder(messageManager, ItemStack(type, amount, data))
    if (DEFAULT_NAME_IDENTIFIER in map) {
        builder.setName(map.getOrException(DEFAULT_NAME_IDENTIFIER))
    }
    if (DEFAULT_SHIROI_ITEM_LORE_IDENTIFIER in map) {
        builder.setLore(map.getOrException(DEFAULT_SHIROI_ITEM_LORE_IDENTIFIER))
    }
    return builder.build()
}
