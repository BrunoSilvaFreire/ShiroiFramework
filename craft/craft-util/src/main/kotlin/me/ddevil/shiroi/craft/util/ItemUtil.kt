package me.ddevil.shiroi.craft.util

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack



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
    override fun isCursed() = false

    override fun getName() = "glow"

    override fun isTreasure() = false

    override fun getMaxLevel() = 1

    override fun conflictsWith(p0: Enchantment?) = false

    override fun canEnchantItem(p0: ItemStack?) = true

    override fun getItemTarget() = EnchantmentTarget.ALL

    override fun getStartLevel() = 1
}

