package me.ddevil.shiroi.craft.event

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

open class ShiroiEvent : Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
            @JvmStatic
            get
    }

    fun call() {
        Bukkit.getPluginManager().callEvent(this)
    }


}