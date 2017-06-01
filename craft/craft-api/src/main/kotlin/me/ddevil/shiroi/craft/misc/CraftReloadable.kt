package me.ddevil.shiroi.craft.misc

import me.ddevil.shiroi.util.misc.Reloadable
import org.bukkit.command.CommandSender

interface CraftReloadable : Reloadable {
    fun reload(sender: CommandSender)
}