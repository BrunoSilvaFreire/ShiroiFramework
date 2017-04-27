package me.ddevil.shiroi.craft.util

import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.material.Wool

class NormalItemBuilder : ItemBuilder {

    override var item: ItemStack

    constructor(item: ItemStack) {
        this.item = item
    }

    @JvmOverloads
    constructor(m: Material, amount: Int = 1) {
        item = ItemStack(m, amount)
    }

    override fun clone(): ItemBuilder = NormalItemBuilder(item)

    override fun setDurability(dur: Short): ItemBuilder {
        item.durability = dur
        return this
    }

    override fun setName(name: String?): ItemBuilder {
        val im = item.itemMeta
        im.displayName = name
        item.itemMeta = im
        return this
    }

    /**
     * Sets the [Color] of a part of leather armor

     * @param color the [Color] to use
     * *
     * @return this builder for chaining
     * *
     * @since 1.1
     */
    override fun color(color: Color): ItemBuilder {
        val type = item.type
        if (type == Material.LEATHER_BOOTS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET
                || type == Material.LEATHER_LEGGINGS) {
            val meta = item.itemMeta as LeatherArmorMeta
            meta.color = color
            item.itemMeta = meta
        } else if (type == Material.WOOL) {
            val wool = item.data as Wool
            wool.color = DyeColor.getByColor(color)
        } else if (type == Material.STAINED_CLAY || type == Material.STAINED_GLASS_PANE || type == Material.STAINED_CLAY) {
            item.data.data = DyeColor.getByColor(color).woolData
        } else {
            throw IllegalArgumentException("color() only applicable for colorable things!")
        }
        return this
    }


    override fun addUnsafeEnchantment(ench: Enchantment, level: Int): ItemBuilder {
        item.addUnsafeEnchantment(ench, level)
        return this
    }

    override fun removeEnchantment(ench: Enchantment): ItemBuilder {
        item.removeEnchantment(ench)
        return this
    }

    override fun setSkullOwner(owner: String): ItemBuilder {
        try {
            val im = item.itemMeta as SkullMeta
            im.owner = owner
            item.itemMeta = im
        } catch (expected: ClassCastException) {
        }

        return this
    }

    override fun addEnchant(ench: Enchantment, level: Int): ItemBuilder {
        val im = item.itemMeta
        im.addEnchant(ench, level, true)
        item.itemMeta = im
        return this
    }

    override fun setInfinityDurability(): ItemBuilder {
        item.durability = java.lang.Short.MAX_VALUE
        return this
    }

    override fun setLore(lore: List<String>?): ItemBuilder {
        val im = item.itemMeta
        im.lore = lore
        item.itemMeta = im
        return this
    }

    /**
     * Clears the lore of the [ItemStack]

     * @return this builder for chaining
     * *
     * @since 1.0
     */
    override fun clearLore(): ItemBuilder {
        val meta = item.itemMeta
        meta.lore = emptyList<String>()
        item.itemMeta = meta
        return this
    }

    /**
     * Clears the list of [Enchantment]s of the [ItemStack]

     * @return this builder for chaining
     * *
     * @since 1.0
     */
    override fun clearEnchantments(): ItemBuilder {
        for (e in item.enchantments.keys) {
            item.removeEnchantment(e)
        }
        return this
    }

    override fun build(): ItemStack {
        return item
    }

    companion object {

        @Throws(IllegalArgumentException::class)
        fun createItem(itemSection: ConfigurationSection): ItemBuilder {
            try {
                var itemName: String? = null
                if (itemSection.contains("name")) {
                    itemName = itemSection.getString("name")
                }
                var itemLore: List<String>? = null
                if (itemSection.contains("lore")) {
                    itemLore = itemSection.getStringList("lore")
                }
                val m = Material.valueOf(itemSection.getString("type"))
                val data = itemSection.getInt("data").toByte()
                val itemBuilder = NormalItemBuilder(ItemStack(m, 1, 0.toShort(), data))
                if (itemLore != null) {
                    itemBuilder.setLore(itemLore)
                }
                if (itemName != null) {
                    itemBuilder.setName(itemName)
                }
                return itemBuilder
            } catch (e: Exception) {

                throw IllegalStateException("Configuration Section " + itemSection.currentPath + " is baddly formated!",
                        e)
            }

        }
    }
}


