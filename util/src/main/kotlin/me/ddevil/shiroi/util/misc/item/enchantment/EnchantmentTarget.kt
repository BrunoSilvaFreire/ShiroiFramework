package me.ddevil.shiroi.util.misc.item.enchantment

import me.ddevil.shiroi.util.misc.item.Item
import me.ddevil.shiroi.util.misc.item.Material

enum class EnchantmentTarget {
    /**
     * Allows the Enchantment to be placed on all items
     */
    ALL {
        override fun includes(item: Material) = true
    },

    /**
     * Allows the Enchantment to be placed on armor
     */
    ARMOR {
        override fun includes(item: Material) = ARMOR_FEET.includes(item)
                || ARMOR_LEGS.includes(item)
                || ARMOR_HEAD.includes(item)
                || ARMOR_TORSO.includes(item)
    },

    /**
     * Allows the Enchantment to be placed on feet slot armor
     */
    ARMOR_FEET {
        override fun includes(item: Material) = item == Material.LEATHER_BOOTS
                || item == Material.CHAINMAIL_BOOTS
                || item == Material.IRON_BOOTS
                || item == Material.DIAMOND_BOOTS
                || item == Material.GOLD_BOOTS
    },

    /**
     * Allows the Enchantment to be placed on leg slot armor
     */
    ARMOR_LEGS {
        override fun includes(item: Material) = item == Material.LEATHER_LEGGINGS
                || item == Material.CHAINMAIL_LEGGINGS
                || item == Material.IRON_LEGGINGS
                || item == Material.DIAMOND_LEGGINGS
                || item == Material.GOLD_LEGGINGS
    },

    /**
     * Allows the Enchantment to be placed on torso slot armor
     */
    ARMOR_TORSO {
        override fun includes(item: Material) = item == Material.LEATHER_CHESTPLATE
                || item == Material.CHAINMAIL_CHESTPLATE
                || item == Material.IRON_CHESTPLATE
                || item == Material.DIAMOND_CHESTPLATE
                || item == Material.GOLD_CHESTPLATE
    },

    /**
     * Allows the Enchantment to be placed on head slot armor
     */
    ARMOR_HEAD {
        override fun includes(item: Material) = item == Material.LEATHER_HELMET
                || item == Material.CHAINMAIL_HELMET
                || item == Material.DIAMOND_HELMET
                || item == Material.IRON_HELMET
                || item == Material.GOLD_HELMET
    },

    /**
     * Allows the Enchantment to be placed on weapons (swords)
     */
    WEAPON {
        override fun includes(item: Material) = item == Material.WOOD_SWORD
                || item == Material.STONE_SWORD
                || item == Material.IRON_SWORD
                || item == Material.DIAMOND_SWORD
                || item == Material.GOLD_SWORD
    },

    /**
     * Allows the Enchantment to be placed on tools (spades, pickaxe, hoes,
     * axes)
     */
    TOOL {
        override fun includes(item: Material) = item == Material.WOOD_SPADE
                || item == Material.STONE_SPADE
                || item == Material.IRON_SPADE
                || item == Material.DIAMOND_SPADE
                || item == Material.GOLD_SPADE
                || item == Material.WOOD_PICKAXE
                || item == Material.STONE_PICKAXE
                || item == Material.IRON_PICKAXE
                || item == Material.DIAMOND_PICKAXE
                || item == Material.GOLD_PICKAXE
                || item == Material.WOOD_HOE         // NOTE: No vanilla enchantments for this
                || item == Material.STONE_HOE        // NOTE: No vanilla enchantments for this
                || item == Material.IRON_HOE         // NOTE: No vanilla enchantments for this
                || item == Material.DIAMOND_HOE      // NOTE: No vanilla enchantments for this
                || item == Material.GOLD_HOE         // NOTE: No vanilla enchantments for this
                || item == Material.WOOD_AXE
                || item == Material.STONE_AXE
                || item == Material.IRON_AXE
                || item == Material.DIAMOND_AXE
                || item == Material.GOLD_AXE
                || item == Material.SHEARS           // NOTE: No vanilla enchantments for this
                || item == Material.FLINT_AND_STEEL // NOTE: No vanilla enchantments for this
    },

    /**
     * Allows the Enchantment to be placed on bows.
     */
    BOW {
        override fun includes(item: Material) = item == Material.BOW
    },

    /**
     * Allows the Enchantment to be placed on fishing rods.
     */
    FISHING_ROD {
        override fun includes(item: Material) = item == Material.FISHING_ROD
    },

    /**
     * Allows the enchantment to be placed on items with durability.
     */
    BREAKABLE {
        override fun includes(item: Material) = item.maxDurability > 0 && item.maxStackSize == 1
    };

    /**
     * Check whether this target includes the specified item.

     * @param item The item to check
     * *
     * @return True if the target includes the item
     */
    abstract fun includes(item: Material): Boolean

    /**
     * Check whether this target includes the specified item.

     * @param item The item to check
     * *
     * @return True if the target includes the item
     */
    fun includes(item: Item) = includes(item.material)
}