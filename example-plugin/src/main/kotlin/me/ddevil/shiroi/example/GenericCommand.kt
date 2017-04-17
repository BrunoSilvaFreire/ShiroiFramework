package me.ddevil.shiroi.example

import me.ddevil.shiroi.craft.command.Command
import me.ddevil.shiroi.craft.command.CommandArgs
import me.ddevil.shiroi.craft.command.ShiroiCommand
import me.ddevil.shiroi.example.ui.GenericUIConstants

class GenericCommand(plugin: GenericPlugin) : ShiroiCommand<GenericPlugin>(plugin) {

    @Command(name = "test")
    fun exampleCommand(args: CommandArgs) {
        GenericUIConstants.MENU.open(args.player)
    }
}