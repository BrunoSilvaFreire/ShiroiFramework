package me.ddevil.shiroi.util.misc.item.enchantment

/**
 * Decreases the rate of air loss whilst underwater
 */
class EnchantmentOxygen : Enchantment(5, "OXYGEN", "oxygen", EnchantmentTarget.ARMOR_HEAD, 3)

/**
 * Actually called "Aqua Affinity"
 *
 * Increases the speed at which a player may mine underwater
 */
class EnchantmentWaterWorker : Enchantment(6, "WATER_WORKER", "waterWorker", EnchantmentTarget.ARMOR_HEAD, 1)

/**
 * Increases walking speed while in water
 */
class EnchantmentThorns : Enchantment(7, "THORNS", "thorns", EnchantmentTarget.ARMOR_TORSO, 3)

class EnchantmentDepthStrider : Enchantment(8, "DEPTH_STRIDER", "waterWalker", EnchantmentTarget.ARMOR_FEET, 1)

class EnchantmentFrostWalker : Enchantment(9, "FROST_WALKER", "frostWalker", EnchantmentTarget.ARMOR_FEET, 1)

class EnchantmentBindingCurse : Enchantment(10, "BINDING_CURSE", "binding_curse", EnchantmentTarget.ARMOR_FEET, 1)
