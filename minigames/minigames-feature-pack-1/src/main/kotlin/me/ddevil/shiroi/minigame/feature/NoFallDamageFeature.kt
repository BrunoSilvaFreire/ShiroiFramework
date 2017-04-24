package me.ddevil.shiroi.minigame.feature

import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent


class NoFallDamageFeature : AbstractFeature("noFallDamage", "No Fall Damage", listOf("")) {
    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        if (e.cause == EntityDamageEvent.DamageCause.FALL) {
            e.isCancelled = true
        }
    }
}