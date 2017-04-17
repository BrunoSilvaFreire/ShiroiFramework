package me.ddevil.shiroi.craft.internal.command

import me.ddevil.shiroi.craft.command.*
import me.ddevil.shiroi.craft.command.Command
import me.ddevil.shiroi.craft.command.internal.DebugCommand
import me.ddevil.shiroi.craft.command.internal.ReloadCommand
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.entity.Player
import org.bukkit.plugin.SimplePluginManager

open class SimpleCommandManager(val plugin: ShiroiPlugin<*, *>) : CommandManager, CommandExecutor, TabCompleter {
    override val loadedCommands: Set<CommandInfo>
        get() = commands.values.toSet()


    val messageManager = plugin.messageManager
    val commands: MutableMap<String, CommandInfo> = mutableMapOf()
    private val commandMap: CommandMap

    init {
        val manager = plugin.server.pluginManager as? SimplePluginManager ?: throw IllegalStateException("Unknown plugin manager!")
        val field = SimplePluginManager::class.java.getDeclaredField("commandMap")
        field.isAccessible = true
        commandMap = field.get(manager) as CommandMap
    }

    override fun reload() {
    }

    override fun enable() {
        registerCommand(DebugCommand(plugin))
        registerCommand(ReloadCommand(plugin))
    }

    override fun disable() {

    }

    override fun registerCommand(command: ShiroiCommand<*>) {
        for (method in command.javaClass.methods) {
            val annotation = method.getAnnotation(Command::class.java) ?: continue
            val info = CommandInfo.fromCommand(annotation, method, command)
            val name = info.name
            for (alias in plugin.allKnownAliases) {
                registerCommand(info, "$alias.$name")
            }
            for (calias in info.aliases) {
                registerCommand(info, "$calias.${info.name}")
            }
        }
    }

    fun registerCommand(command: CommandInfo, label: String) {
        val label = label.toLowerCase()
        commands.put(label, command)
        commands.put("${this.plugin.name}:$label", command)
        val cmdLabel = label.split("\\.".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()[0].toLowerCase()
        if (commandMap.getCommand(cmdLabel) == null) {
            val cmd = BukkitCommand(cmdLabel, this, this, plugin)
            cmd.description = command.description
            cmd.usage = command.usage
            commandMap.register(plugin.name, cmd)
        }
    }

    override fun onCommand(sender: CommandSender,
                           cmd: org.bukkit.command.Command,
                           label: String,
                           args: Array<String>): Boolean {
        //Loop backwards to find command
        for (i in args.size downTo 0) {
            // build command name
            val buffer = StringBuilder()
            buffer.append(label.toLowerCase())
            for (x in 0 .. i - 1) {
                buffer.append(".").append(args[x].toLowerCase())
            }
            val cmdLabel = buffer.toString()
            // if command exists, execute it
            if (commands.containsKey(cmdLabel)) {
                val entry = commands[cmdLabel]
                val command = entry!!
                val method = command.method
                if (command.permission !== "" && !sender.hasPermission(command.permission)) {
                    sender.sendMessage(command.noPerm)
                    return true
                }
                if (command.inGameOnly && sender !is Player) {
                    sender.sendMessage("This command is only performable in game")
                    return true
                }
                try {
                    // fix arguments
                    method.invoke(entry.owner, CommandArgs(
                            sender,
                            cmd,
                            label,
                            args,
                            cmdLabel.split("\\.".toRegex()).dropLastWhile(String::isEmpty).toTypedArray().size - 1,
                            plugin))
                } catch (e: Exception) {
                    plugin.pluginLogger.printException("There was a problem while executing command '$cmdLabel'", e)
                }

                return true
            }
        }
        messageManager.sendMessage(sender, "$4Unknown command.")
        return true
    }

    override fun onTabComplete(sender: CommandSender,
                               commandA: org.bukkit.command.Command,
                               label: String,
                               args: Array<out String>): List<String> {
        if (commands.containsKey(args.joinToString("."))) {
            return emptyList()
        }
        val possibleComands = mutableListOf<String>()
        commandLoop@ for ((key) in commands) {
            Bukkit.broadcastMessage("Checking command '$key'")
            val commandArguments = key.split('.')
            var commandArg: String? = null
            for (i in 0 .. args.size - 1) {
                if (commandArguments.size - 1 >= i) {
                    val currentArgument = args[i]
                    commandArg = commandArguments[i]
                    Bukkit.broadcastMessage("Checking argument '$currentArgument' vs '$commandArg' @$i")
                    if (!commandArg.equals(currentArgument, true)) {
                        if (!possibleComands.contains(commandArg)) {
                            Bukkit.broadcastMessage("Adding $commandArg")
                            possibleComands.add(commandArg)
                        } else {
                            Bukkit.broadcastMessage("Already contains $commandArg")
                        }
                    } else {
                        Bukkit.broadcastMessage("Different, continuing")
                    }
                } else {
                    Bukkit.broadcastMessage("Size too small, ${commandArguments.size - 1}/$i")
                }
            }
            possibleComands.add(commandArg ?: continue@commandLoop)
        }
        return possibleComands
    }
}

class BukkitCommand constructor(label: String,
                                private val executor: CommandExecutor,
                                val completer: TabCompleter,
                                private val owningPlugin: ShiroiPlugin<*, *>) : org.bukkit.command.Command(label) {
    init {
        this.usageMessage = ""
    }

    override fun tabComplete(sender: CommandSender?,
                             alias: String?,
                             args: Array<out String>?) = completer.onTabComplete(sender, this, alias, args)!!

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        val success: Boolean

        if (!owningPlugin.isEnabled) {
            return false
        }

        if (!testPermission(sender)) {
            return true
        }

        try {
            success = executor.onCommand(sender, this, commandLabel, args)
        } catch (ex: Throwable) {
            throw CommandException("Unhandled exception command '" + commandLabel + "' in plugin "
                    + owningPlugin.description.fullName, ex)
        }

        if (!success && usageMessage.isNotEmpty()) {
            for (line in usageMessage.replace("{command}",
                    commandLabel).split("\n".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()) {
                sender.sendMessage(line)
            }
        }

        return success
    }


}
