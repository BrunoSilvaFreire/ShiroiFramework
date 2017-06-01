package me.ddevil.shiroi.craft.util.misc

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class PlayerDisconnectListener(val action: (Cause) -> (Unit)) : Listener {


    enum class Cause {
        KICK,
        DISCONNECT;
    }

    @EventHandler
    private fun onQuit(playerQuitEvent: PlayerQuitEvent) {
        call(Cause.DISCONNECT)
    }

    @EventHandler
    private fun onKick(playerKickEvent: PlayerKickEvent) {
        call(Cause.KICK)
    }

    private fun call(cause: Cause) {
        action(cause)
        unregister()
    }

    fun register(plugin: Plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    private fun unregister() {
        HandlerList.unregisterAll(this)
    }
}