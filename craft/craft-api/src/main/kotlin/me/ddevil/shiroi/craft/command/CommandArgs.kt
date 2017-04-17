package me.ddevil.shiroi.craft.command

import me.ddevil.shiroi.craft.message.LangMessageManager
import me.ddevil.shiroi.craft.message.lang.Lang
import me.ddevil.shiroi.craft.message.lang.MessageVariable
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandArgs(
        /**
         * Gets the command sender

         * @return
         */
        val sender: CommandSender,
        /**
         * Gets the original command object

         * @return
         */
        val command: Command,
        label: String,
        args: Array<String>,
        subCommand: Int,
        val plugin: ShiroiPlugin<*, *>) {
    /**
     * Gets the label including sub command labels of this command

     * @return Something like 'test.subcommand'
     */
    val label: String
    /**
     * Gets all the arguments after the command's label. ie. if the command
     * label was test.subcommand and the arguments were subcommand foo foo, it
     * would only return 'foo foo' because 'subcommand' is part of the command

     * @return
     */
    val args: Array<String>

    init {
        val modArgs = arrayOfNulls<String>(args.size - subCommand)
        System.arraycopy(args, subCommand, modArgs, 0, args.size - subCommand)
        val buffer = StringBuilder()
        buffer.append(label)
        for (x in 0 .. subCommand - 1) {
            buffer.append(".").append(args[x])
        }
        val cmdLabel = buffer.toString()
        this.label = cmdLabel
        this.args = modArgs.requireNoNulls()
    }

    /**
     * Gets the argument at the specified index

     * @param index The index to translate
     * *
     * @return The string at the specified index
     */
    operator fun get(index: Int) = args[index]

    inline fun getPlayerOr(index: Int, orElse: () -> Unit, action: (Player) -> Unit) {
        if (length() > index) {
            val player = Bukkit.getPlayer(get(index))
            if (player != null) {
                action(player)
                return
            }
        }
        orElse()
    }

    fun getPlayerOrDefault(index: Int, default: Player): Player {
        if (length() > index) {
            return Bukkit.getPlayer(get(index)) ?: default
        }
        return default
    }

    @JvmOverloads
    inline fun joinFromAnd(start: Int, end: Int = args.size - 1, action: (String) -> Unit) {
        var istart = start
        if (length() > start) {
            action("")
            return
        }
        var message = String()
        val containsInitialQuote = get(start)[0] == '"'
        if (containsInitialQuote) {
            istart++
        }
        if (length() > istart) {
            for (i in start .. args.size - 1) {
                if (i > end) {
                    break
                }
                val string = args[i]
                for (char in string) {
                    if (containsInitialQuote && char == '"') {
                        break
                    }
                    message += char
                }
                message += ' '
            }
        }
        action(message)
    }

    inline fun getStringOrElse(index: Int, orElse: () -> Unit, action: (String) -> Unit) {
        if (length() > index) {
            action(get(index))
        } else {
            orElse()
        }
    }

    inline fun getIntOrElse(index: Int,
                            orElse: () -> Unit,
                            invalidInt: (String) -> Unit,
                            action: (Int) -> Unit) {
        getStringOrElse(index, orElse) {
            try {
                action(it.toInt())
            } catch(e: NumberFormatException) {
                invalidInt(it)
            }
        }
    }


    inline fun getLongOrElse(index: Int,
                             orElse: () -> Unit,
                             invalidLong: (String) -> Unit,
                             action: (Long) -> Unit) {
        getStringOrElse(index, orElse) {
            try {
                action(it.toLong())
            } catch(e: NumberFormatException) {
                invalidLong(it)
            }
        }
    }

    inline fun getFloatOrElse(index: Int,
                              orElse: () -> Unit,
                              invalidFloat: (String) -> Unit,
                              action: (Float) -> Unit) {
        getStringOrElse(index, orElse) {
            try {
                action(it.toFloat())
            } catch(e: NumberFormatException) {
                invalidFloat(it)
            }
        }
    }

    inline fun getDoubleOrElse(index: Int,
                               orElse: () -> Unit,
                               invalidDouble: (String) -> Unit,
                               action: (Double) -> Unit) {
        getStringOrElse(index, orElse) {
            try {
                action(it.toDouble())
            } catch(e: NumberFormatException) {
                invalidDouble(it)
            }
        }
    }

    inline fun getStringOrMessage(index: Int, orElse: String, action: (String) -> Unit) {
        getStringOrElse(index, {
            plugin.messageManager.sendMessage(sender, orElse)
        }, action)
    }

    inline fun <L : Lang<*, *>> getStringOrMessage(index: Int,
                                                   lang: L,
                                                   messageManager: LangMessageManager<L>,
                                                   vararg variables: MessageVariable,
                                                   action: (String) -> Unit) {
        getStringOrElse(index, {
            messageManager.sendMessage(sender, lang, *variables)
        }, action)
    }

    inline fun getIntOrMessage(index: Int, orElse: String, invalidInt: String, action: (Int) -> Unit) {
        getIntOrElse(index, {
            plugin.messageManager.sendMessage(sender, orElse)
        }, {
            plugin.messageManager.sendMessage(sender, invalidInt)
        }, action)
    }

    inline fun <L : Lang<*, *>> getIntOrMessage(
            index: Int,
            orElse: L,
            orElseVariables: () -> Array<MessageVariable>,
            invalidInt: L,
            invalidIntVariables: () -> Array<MessageVariable>,
            messageManager: LangMessageManager<L>,
            action: (Int) -> Unit) {
        getIntOrElse(index, {
            messageManager.sendMessage(sender, orElse, *orElseVariables())
        }, {
            messageManager.sendMessage(sender, invalidInt, *invalidIntVariables())
        }, action)
    }


    inline fun getDoubleOrMessage(index: Int, orElse: String, invalidDouble: String, action: (Double) -> Unit) {
        getDoubleOrElse(index, {
            plugin.messageManager.sendMessage(sender, orElse)
        }, {
            plugin.messageManager.sendMessage(sender, invalidDouble)
        }, action)
    }

    inline fun <L : Lang<*, *>> getDoubleOrMessage(
            index: Int,
            orElse: L,
            orElseVariables: () -> Array<MessageVariable>,
            invalidDouble: L,
            invalidDoubleVariables: () -> Array<MessageVariable>,
            messageManager: LangMessageManager<L>,
            action: (Double) -> Unit) {
        getDoubleOrElse(index, {
            messageManager.sendMessage(sender, orElse, *orElseVariables())
        }, {
            messageManager.sendMessage(sender, invalidDouble, *invalidDoubleVariables())
        }, action)
    }

    inline fun getLongOrMessage(index: Int, orElse: String, invalidLong: String, action: (Long) -> Unit) {
        getLongOrElse(index, {
            plugin.messageManager.sendMessage(sender, orElse)
        }, {
            plugin.messageManager.sendMessage(sender, invalidLong)
        }, action)
    }

    inline fun <L : Lang<*, *>> getLongOrMessage(
            index: Int,
            orElse: L,
            orElseVariables: () -> Array<MessageVariable>,
            invalidLong: L,
            invalidLongVariables: () -> Array<MessageVariable>,
            messageManager: LangMessageManager<L>,
            action: (Long) -> Unit) {
        getLongOrElse(index, {
            messageManager.sendMessage(sender, orElse, *orElseVariables())
        }, {
            messageManager.sendMessage(sender, invalidLong, *invalidLongVariables())
        }, action)
    }

    inline fun getFloatOrMessage(index: Int, orElse: String, invalidFloat: String, action: (Float) -> Unit) {
        getFloatOrElse(index, {
            plugin.messageManager.sendMessage(sender, orElse)
        }, {
            plugin.messageManager.sendMessage(sender, invalidFloat)
        }, action)
    }

    inline fun <L : Lang<*, *>> getFloatOrMessage(
            index: Int,
            orElse: L,
            orElseVariables: () -> Array<MessageVariable>,
            invalidFloat: L,
            invalidFloatVariables: () -> Array<MessageVariable>,
            messageManager: LangMessageManager<L>,
            action: (Float) -> Unit) {
        getFloatOrElse(index, {
            messageManager.sendMessage(sender, orElse, *orElseVariables())
        }, {
            messageManager.sendMessage(sender, invalidFloat, *invalidFloatVariables())
        }, action)
    }

    /**
     * Returns the length of the command arguments

     * @return int length of args
     */
    fun length() = args.size

    val isPlayer: Boolean
        get() = sender is Player

    val player: Player
        get() = sender as? Player ?: throw IllegalStateException("Sender is not instance of a player!")
}