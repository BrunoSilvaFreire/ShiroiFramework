package me.ddevil.shiroi.craft.command

import me.ddevil.shiroi.util.misc.Reloadable

interface CommandManager : Reloadable {
    fun registerCommand(command: ShiroiCommand<*>)
    val loadedCommands: Set<CommandInfo>
}