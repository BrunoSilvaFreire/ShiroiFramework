package me.ddevil.shiroi.util.misc.item.enchantment

import me.ddevil.shiroi.util.misc.item.Item


abstract class Enchantment
@JvmOverloads
protected constructor(
        val id: Int,
        val name: String,
        val nmsName: String,
        val itemTarget: EnchantmentTarget = EnchantmentTarget.ALL,
        val maxLevel: Int,
        val startLevel: Int = 1,
        val treasure: Boolean = false,
        val cursed: Boolean = false
) {

    open fun conflictsWith(other: Enchantment) = false

    open fun canEnchantItem(item: Item) = itemTarget.includes(item)

    companion object {

        val PROTECTION_ENVIROMENTAL = EnchantmentProtectionEnviromental()

        val PROTECTION_FIRE = EnchantmentProtectionFire()

        val PROTECTION_FALL = EnchantmentProtectionFall()

        val PROTECTION_EXPLOSION = EnchantmentProtectionExplosion()

        val PROTECTION_PROJECTILE = EnchantmentProtectionProjectile()

        val OXYGEN = EnchantmentOxygen()

        val WATER_WORKER = EnchantmentWaterWorker()

        val THORNS = EnchantmentThorns()

        val DEPTH_STRIDER = EnchantmentDepthStrider()

        val FROST_WALKER = EnchantmentFrostWalker()

        val BINDING_CURSE = EnchantmentBindingCurse()


    }
}