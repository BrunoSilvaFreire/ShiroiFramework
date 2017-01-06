package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.util.misc.Reloadable
import org.bukkit.command.CommandSender

interface MessageManager : Reloadable {

    operator fun get(i: Int): Char

    fun sendMessage(p: CommandSender, vararg messages: String)

    fun broadcastMessage(vararg messages: String)

    fun translateTags(input: String): String

    fun translateColors(input: String): String

    fun translateAll(input: String): String

    fun translateTags(input: Iterable<String>): List<String>

    fun translateColors(input: Iterable<String>): List<String>

    fun translateAll(input: Iterable<String>): List<String>

}