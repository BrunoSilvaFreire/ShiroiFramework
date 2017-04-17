package me.ddevil.shiroi.effect

import me.ddevil.shiroi.util.misc.MinecraftVersion

enum class ParticleType
constructor(
        val particleName: String,
        val minimumVersion: MinecraftVersion = MinecraftVersion.V1_7,
        vararg val features: ParticleFeature = emptyArray()
) {
    EXPLOSION_NORMAL("explode"),
    EXPLOSION_LARGE("largeexplode"),
    EXPLOSION_HUGE("hugeexplosion"),
    FIREWORKS_SPARK("fireworksSpark"),
    WATER_BUBBLE("bubble"),
    WATER_SPLASH("splash"),
    WATER_WAKE("wake"),
    SUSPENDED("suspended"),
    SUSPENDED_DEPTH("depthsuspend"),
    CRIT("crit"),
    CRIT_MAGIC("magicCrit"),
    SMOKE_NORMAL("smoke"),
    SMOKE_LARGE("largesmoke"),
    SPELL("spell"),
    SPELL_INSTANT("instantSpell"),
    SPELL_MOB("mobSpell", MinecraftVersion.V1_7, ParticleFeature.COLOR),
    SPELL_MOB_AMBIENT("mobSpellAmbient"),
    SPELL_WITCH("witchMagic"),
    DRIP_WATER("dripWater"),
    DRIP_LAVA("dripLava"),
    VILLAGER_ANGRY("angryVillager"),
    VILLAGER_HAPPY("happyVillager"),
    TOWN_AURA("townaura"),
    NOTE("note", MinecraftVersion.V1_7, ParticleFeature.COLOR),
    PORTAL("portal"),
    ENCHANTMENT_TABLE("enchantmenttable"),
    FLAME("flame"),
    LAVA("lava"),
    FOOTSTEP("footstep"),
    CLOUD("cloud"),
    REDSTONE("reddust", MinecraftVersion.V1_7, ParticleFeature.COLOR),
    SNOWBALL("snowballpoof"),
    SNOW_SHOVEL("snowshovel"),
    SLIME("slime"),
    HEART("heart"),
    BARRIER("barrier", MinecraftVersion.V1_8),
    ITEM_CRACK("iconcrack_", MinecraftVersion.V1_7, ParticleFeature.DATA),
    BLOCK_CRACK("blockcrack_", MinecraftVersion.V1_7, ParticleFeature.DATA),
    BLOCK_DUST("blockdust_", MinecraftVersion.V1_7, ParticleFeature.DATA),
    WATER_DROP("droplet", MinecraftVersion.V1_8),
    ITEM_TAKE("take", MinecraftVersion.V1_8),
    MOB_APPEARANCE("mobappearance", MinecraftVersion.V1_8),
    DRAGON_BREATH("dragonbreath", MinecraftVersion.V1_9),
    END_ROD("endRod", MinecraftVersion.V1_9),
    DAMAGE_INDICATOR("damageIndicator", MinecraftVersion.V1_9),
    SWEEP_ATTACK("sweepAttack", MinecraftVersion.V1_9),
    FALLING_DUST("fallingDust", MinecraftVersion.V1_10);

    fun hasFeature(feature: ParticleFeature): Boolean {
        return this.features.contains(feature)
    }
}
