package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.message.lang.Lang
import me.ddevil.shiroi.craft.message.lang.LangRequest
import org.bukkit.command.CommandSender

interface LangMessageManager<in L : Lang<R>, in R : LangRequest> : MessageManager {

    fun sendMessage(p: CommandSender, request: R, vararg messages: L)

    fun getLang(message: L, request: R): String

}