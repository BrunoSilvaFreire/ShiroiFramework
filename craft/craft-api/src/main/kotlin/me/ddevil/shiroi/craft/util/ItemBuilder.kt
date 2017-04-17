package me.ddevil.shiroi.craft.util

import me.ddevil.shiroi.craft.message.MessageManager
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.material.Wool

class ItemBuilder {
    private val messageManager: MessageManager?

    private var item: ItemStack

    @JvmOverloads
    constructor(m: Material, messageManager: MessageManager? = null) : this(m, 1, messageManager)

    @JvmOverloads
    constructor(item: ItemStack, messageManager: MessageManager? = null) {
        this.messageManager = messageManager
        this.item = item
    }

    @JvmOverloads
    constructor(m: Material, amount: Int, messageManager: MessageManager? = null) {
        this.messageManager = messageManager
        item = ItemStack(m, amount)
    }

    fun clone(): ItemBuilder {
        return ItemBuilder(item, messageManager)
    }

    fun setDurability(dur: Short): ItemBuilder {
        item.durability = dur
        return this
    }

    fun setName(name: String): ItemBuilder {
        var name = name
        val im = item.itemMeta
        if (messageManager != null) {
            name = messageManager.translateAll(name)
        }
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
    fun color(color: Color): ItemBuilder {
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


    fun addUnsafeEnchantment(ench: Enchantment, level: Int): ItemBuilder {
        item.addUnsafeEnchantment(ench, level)
        return this
    }

    fun removeEnchantment(ench: Enchantment): ItemBuilder {
        item.removeEnchantment(ench)
        return this
    }

    fun setSkullOwner(owner: String): ItemBuilder {
        try {
            val im = item.itemMeta as SkullMeta
            im.owner = owner
            item.itemMeta = im
        } catch (expected: ClassCastException) {
        }

        return this
    }

    fun addEnchant(ench: Enchantment, level: Int): ItemBuilder {
        val im = item.itemMeta
        im.addEnchant(ench, level, true)
        item.itemMeta = im
        return this
    }

    fun setInfinityDurability(): ItemBuilder {
        item.durability = java.lang.Short.MAX_VALUE
        return this
    }

    fun setLore(lore: List<String>): ItemBuilder {
        var lore = lore
        val im = item.itemMeta
        if (messageManager != null) {
            lore = messageManager.translateAll(lore)
        }
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
    fun clearLore(): ItemBuilder {
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
    fun clearEnchantments(): ItemBuilder {
        for (e in item.enchantments.keys) {
            item.removeEnchantment(e)
        }
        return this
    }

    fun toItemStack(): ItemStack {
        return item
    }

    companion object {

        @Throws(IllegalArgumentException::class)
        fun createItem(itemSection: ConfigurationSection?, messageManager: MessageManager): ItemBuilder {
            try {
                var itemName: String? = null
                if (itemSection!!.contains("name")) {
                    itemName = messageManager.translateAll(itemSection.getString("name"))
                }
                var itemLore: List<String>? = null
                if (itemSection.contains("lore")) {
                    itemLore = messageManager.translateAll(itemSection.getStringList("lore"))
                }
                val m = Material.valueOf(itemSection.getString("type"))
                val data = itemSection.getInt("data").toByte()
                val itemBuilder = ItemBuilder(ItemStack(m, 1, 0.toShort(), data), messageManager)
                if (itemLore != null) {
                    itemBuilder.setLore(itemLore)
                }
                if (itemName != null) {
                    itemBuilder.setName(itemName)
                }
                return itemBuilder
            } catch (e: Exception) {
                if (itemSection == null) {
                    throw IllegalArgumentException("Given configuration section is null! ", e)
                } else {
                    throw IllegalStateException("Configuration Section " + itemSection.currentPath + " is baddly formated!",
                            e)
                }
            }

        }
    }
}


