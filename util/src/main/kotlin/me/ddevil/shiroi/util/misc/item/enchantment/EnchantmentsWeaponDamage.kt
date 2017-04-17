package me.ddevil.shiroi.util.misc.item.enchantment

abstract class EnchantmentWeaponDamage(id: Int, name: String, nmsSubName: String) : Enchantment(id,
        name,
        "enchantment.damage.$nmsSubName",
        EnchantmentTarget.WEAPON,
        5)

class EnchantmentDamageAll : EnchantmentWeaponDamage(16, "DAMAGE_ALL", "sharpness")

class EnchantmentDamageUndead : EnchantmentWeaponDamage(17, "DAMAGE_UNDEAD", "smite")

class EnchantmentDamageArthropods : EnchantmentWeaponDamage(18, "DAMAGE_ARTHROPODS", "bane_of_arthropods")

