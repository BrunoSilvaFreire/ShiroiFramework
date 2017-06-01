package me.ddevil.shiroi.craft.command.internal

import me.ddevil.shiroi.craft.command.Command
import me.ddevil.shiroi.craft.command.CommandArgs
import me.ddevil.shiroi.craft.command.ShiroiCommand
import me.ddevil.shiroi.craft.event.ShiroiPluginReloadEvent
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin

class ReloadCommand(plugin: ShiroiPlugin<*, *>) : ShiroiCommand<ShiroiPlugin<*, *>>(plugin) {
    private var messageManager: MessageManager = plugin.messageManager

    @Command("reload", "cmd.reload")
    fun reload(args: CommandArgs) {
        val sender = args.sender
        messageManager.sendMessage(sender, "$3Reloading plugin...")
        val start = System.currentTimeMillis()
        plugin.reload(sender)
        val end = System.currentTimeMillis()
        val duration = end - start
        ShiroiPluginReloadEvent(plugin).call()
        messageManager.sendMessage(sender,
                "$3Reload complete! Took $1$duration$3ms! ($2${(duration / 1000).toInt()}$3s)")
    }
}