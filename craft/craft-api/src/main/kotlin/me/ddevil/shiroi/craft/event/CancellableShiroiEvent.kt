package me.ddevil.shiroi.craft.event

import org.bukkit.event.Cancellable

class CancellableShiroiEvent : ShiroiEvent(), Cancellable {
    private var cancelled: Boolean = false

    override fun setCancelled(p0: Boolean) {
        this.cancelled = p0
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

}