package me.ddevil.shiroi.util.misc.item.enchantment

import me.ddevil.shiroi.util.misc.item.Item


abstract class EnchantmentProtection(id: Int, bukkitName: String, nmsSubName: String) : Enchantment(id,
        bukkitName,
        "enchantment.protect.$nmsSubName",
        EnchantmentTarget.ARMOR,
        4) {

    override fun conflictsWith(other: Enchantment) = other is EnchantmentProtection

    override fun canEnchantItem(item: Item) = true

}

/**
 * Provides protection against environmental damage
 */
class EnchantmentProtectionEnviromental : EnchantmentProtection(0, "PROTECTION_ENVIRONMENTAL", "all")

/**
 * Provides protection against fire damage
 */
class EnchantmentProtectionFire : EnchantmentProtection(1, "PROTECTION_FIRE", "fire")

/**
 * Provides protection against fall damage
 */
class EnchantmentProtectionFall : EnchantmentProtection(2, "PROTECTION_FALL", "fall")

/**
 * Provides protection against explosive damage
 */
class EnchantmentProtectionExplosion : EnchantmentProtection(3, "PROTECTION_EXPLOSIONS", "explosion")

/**
 * Provides protection against projectile damage
 */
class EnchantmentProtectionProjectile : EnchantmentProtection(4, "PROTECTION_PROJECTILE", "projectile")
