package me.ddevil.shiroi.ui.api.event

import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class UIActionEvent
constructor(val component: Clickable,
            val clickedSlot: UIPosition,
            val player: Player,
            val clickType: InteractionType,
            val placedBlock: Block?) : Event() {

    fun call(): UIActionEvent {
        Bukkit.getPluginManager().callEvent(this)
        return this
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        val handlerList = HandlerList()
    }

}
