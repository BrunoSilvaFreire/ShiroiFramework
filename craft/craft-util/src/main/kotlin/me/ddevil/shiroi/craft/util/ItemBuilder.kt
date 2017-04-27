package me.ddevil.shiroi.craft.util

import org.bukkit.Color
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

interface ItemBuilder {
    var item: ItemStack
    fun clone(): ItemBuilder
    fun setDurability(dur: Short): ItemBuilder
    fun setName(name: String?): ItemBuilder
    /**
     * Sets the [Color] of a part of leather armor

     * @param color the [Color] to use
     * *
     * @return this builder for chaining
     * *
     * @since 1.1
     */
    fun color(color: Color): ItemBuilder

    fun addUnsafeEnchantment(ench: Enchantment, level: Int): ItemBuilder

    fun removeEnchantment(ench: Enchantment): ItemBuilder

    fun setSkullOwner(owner: String): ItemBuilder

    fun addEnchant(ench: Enchantment, level: Int): ItemBuilder

    fun setInfinityDurability(): ItemBuilder

    fun setLore(lore: List<String>?): ItemBuilder
    /**
     * Clears the lore of the [ItemStack]

     * @return this builder for chaining
     * *
     * @since 1.0
     */
    fun clearLore(): ItemBuilder

    /**
     * Clears the list of [Enchantment]s of the [ItemStack]

     * @return this builder for chaining
     * *
     * @since 1.0
     */
    fun clearEnchantments(): ItemBuilder

    fun build(): ItemStack
}