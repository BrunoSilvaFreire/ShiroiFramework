package me.ddevil.shiroi.craft.command.internal

import me.ddevil.shiroi.craft.command.Command
import me.ddevil.shiroi.craft.command.CommandArgs
import me.ddevil.shiroi.craft.command.ShiroiCommand
import me.ddevil.shiroi.craft.log.DebugLevel
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.util.enumValueWithIndex

class DebugCommand(plugin: ShiroiPlugin<*, *>) : ShiroiCommand<ShiroiPlugin<*, *>>(plugin) {
    private var messageManager: MessageManager = this.plugin.messageManager

    @Command(name = "debug.level", permission = "cmd.debug.level")
    fun debugLevel(args: CommandArgs) {
        val sender = args.sender
        args.getIntOrElse(0, {
            val level = plugin.pluginLogger.minimumDebugLevel
            plugin.messageManager.sendMessage(sender, "Current debug level: $1$level")
        }, {
            plugin.messageManager.sendMessage(sender, "$4You must provide a valid integer!")
        }) {
            val newLevel = enumValueWithIndex<DebugLevel>(it)
            plugin.pluginLogger.minimumDebugLevel = newLevel
            messageManager.sendMessage(sender, "Debug level was set to $1$newLevel")
        }
    }

    @Command(name = "debug", permission = "cmd.debug")
    fun debugInfo(args: CommandArgs) {
        val sender = args.sender
        messageManager.sendMessage(sender,
                "$3Debug info for prefix: $1${plugin.name}",
                "",
                "$3[$1Plugin Internal Settings$3]",
                "$3Primary Acronym: $2${plugin.settings.primaryAcronym}",
                "$3Secondary Acronyms: ${plugin.settings.aliases.joinToString(separator = "$3, $2",
                        prefix = "$2",
                        postfix = "$3")}",
                "",
                "$3[$1Shiroi Master Config$3]",
                "$3Ignores Master Config: $2${plugin.settings.ignoreMasterConfig}",
                "$3Using Master Color: $2${plugin.masterConfig.useMasterColor}",
                "",
                "$3[$1Shiroi Master Color$3]",
                "$3Master Design Primary Color: ${plugin.masterConfig.masterColor.primaryColor}${plugin.masterConfig.masterColor.primaryColor.name}",
                "$3Master Design Secondary Color: ${plugin.masterConfig.masterColor.secondaryColor}${plugin.masterConfig.masterColor.secondaryColor.name}",
                "",
                "$3[$1Color $2Design$3]",
                "$3Primary Color: $1${plugin.colorDesign.primaryColor.name}",
                "$3Secondary Color: $2${plugin.colorDesign.secondaryColor.name}",
                "",
                "$3[$1Debug Info$3]",
                "$3Default Minimum Debug Level: $2${plugin.pluginLogger.defaultDebugLevel.ordinal}",
                "$3Current Minimum Debug Level: $2${plugin.pluginLogger.minimumDebugLevel.ordinal}",
                ""

        )
        messageManager.sendMessage(sender,
                "$3[$1Commands$3]"
        )
        plugin.commandManager.loadedCommands.forEach {
            messageManager.sendMessage(sender,
                    "$1${it.name}$3@$2${it.owner.javaClass.simpleName}$3#$2${it.method.name}")
        }

    }
}