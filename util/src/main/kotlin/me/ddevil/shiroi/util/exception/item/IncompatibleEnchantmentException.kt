package me.ddevil.shiroi.util.exception.item

import me.ddevil.shiroi.util.misc.item.enchantment.Enchantment

class IncompatibleEnchantmentException(val enchantment: Enchantment) : IllegalArgumentException("There are incompatible enchantments with $enchantment in this item!")