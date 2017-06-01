package me.ddevil.shiroi.craft.command

import me.ddevil.shiroi.craft.misc.CraftReloadable

interface CommandManager : CraftReloadable {
    fun registerCommand(command: ShiroiCommand<*>)
    val loadedCommands: Set<CommandInfo>
}