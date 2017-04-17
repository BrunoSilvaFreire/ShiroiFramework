package me.ddevil.shiroi.craft.command

import java.lang.reflect.Method

/**
 * Created by bruno on 10/12/2016.
 */
class CommandInfo(val method: Method,
                  val owner: ShiroiCommand<*>,
                  val name: String,
                  val permission: String,
                  val noPerm: String,
                  val aliases: Array<String>,
                  val description: String,
                  val usage: String,
                  val inGameOnly: Boolean,
                  val inGameOnlyMessage: String) {
    companion object {
        fun fromCommand(command: Command, method: Method, owner: ShiroiCommand<*>): CommandInfo = CommandInfo(method,
                owner,
                command.name,
                command.permission,
                command.noPerm,
                command.aliases,
                command.description,
                command.usage,
                command.inGameOnly,
                command.inGameOnlyMessage)
    }
}
