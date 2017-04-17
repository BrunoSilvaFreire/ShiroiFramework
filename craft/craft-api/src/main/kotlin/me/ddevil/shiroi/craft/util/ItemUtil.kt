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
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack


@JvmOverloads
fun parseConfig(config: ConfigurationSection, messageManager: MessageManager? = null): ItemStack {
    return parseMap(config.toMap(), messageManager)
}

@JvmOverloads
fun parseMap(map: Map<String, Any>, messageManager: MessageManager? = null): ItemStack {
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
    val builder = ItemBuilder(ItemStack(type, amount, data), messageManager)
    if (DEFAULT_NAME_IDENTIFIER in map) {
        builder.setName(map.getOrException(DEFAULT_NAME_IDENTIFIER))
    }
    if (DEFAULT_SHIROI_ITEM_LORE_IDENTIFIER in map) {
        builder.setLore(map.getOrException(DEFAULT_SHIROI_ITEM_LORE_IDENTIFIER))
    }
    return builder.toItemStack()
}

fun parseString(item: String): ItemStack {
    if (item.contains(':')) {
        val split = item.split(':')
        val data: Short
        if (split.size > 1) {
            try {
                data = split[1].toShort()
            } catch (n: NumberFormatException) {
                throw IllegalArgumentException("Malformed short '${split[1]}' in serialized item '$item'!")
            }
        } else {
            data = 0
        }
        return ItemStack(Material.getMaterial(split[0]), 1, data)
    } else {
        return ItemStack(Material.getMaterial(item))
    }
}

private var glowRegistered: Boolean = false

fun ItemStack.addGlow() {
    if (!glowRegistered) {
        registerGlow()
    }
    val glow = Glow(70)
    val im = itemMeta
    im.addEnchant(glow, 1, true)
    itemMeta = im
}

private fun registerGlow() {
    try {
        val f = Enchantment::class.java.getDeclaredField("acceptingNew")
        f.isAccessible = true
        f.set(null, true)
    } catch (e: NoSuchFieldException) {

    } catch (e: SecurityException) {
    } catch (e: IllegalArgumentException) {
    } catch (e: IllegalAccessException) {
    }

    try {
        val glow = Glow(70)
        Enchantment.registerEnchantment(glow)
        glowRegistered = true
    } catch (e: IllegalArgumentException) {
    }

}

private class Glow(id: Int) : Enchantment(id) {
    override fun getName() = "glow"

    override fun isTreasure() = false

    override fun getMaxLevel() = 1

    override fun conflictsWith(p0: Enchantment?) = false

    override fun canEnchantItem(p0: ItemStack?) = true

    override fun getItemTarget() = EnchantmentTarget.ALL

    override fun getStartLevel() = 1
}

