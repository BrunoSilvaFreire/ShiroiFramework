package me.ddevil.shiroi.craft.util.misc

import me.ddevil.shiroi.craft.util.createAndRunBukkitTaskLaterSync
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class Invite(val player: Player, val action: (InviteResult) -> (Unit)) : Listener {


    fun accept() {
        finish(InviteResult.ACCEPTED)
    }

    fun decline() {
        finish(InviteResult.DECLINED)
    }

    private fun finish(result: InviteResult) {
        action(result)
        HandlerList.unregisterAll(this)
    }

    fun send(timeOut: Long, plugin: Plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
        if (timeOut > 0) {
            createAndRunBukkitTaskLaterSync(plugin, timeOut) {
                finish(InviteResult.TIMED_OUT)
            }
        }
    }

    @EventHandler
    private fun onKick(playerKickEvent: PlayerKickEvent) {
        finish(InviteResult.KICKED)

    }

    @EventHandler
    private fun onQuit(playerQuitEvent: PlayerQuitEvent) {
        finish(InviteResult.DISCONNECTED)
    }


    enum class InviteResult constructor(val leftServer: Boolean = false) {
        ACCEPTED,
        DECLINED,
        DISCONNECTED(true),
        KICKED(true),
        TIMED_OUT;
    }
}