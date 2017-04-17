package me.ddevil.shiroi.util.exception.item

import me.ddevil.shiroi.util.misc.item.enchantment.Enchantment

class SimilarEnchantmentException(enchantment: Enchantment) : IllegalArgumentException("There is already a similar enchantment to $enchantment in this item!")