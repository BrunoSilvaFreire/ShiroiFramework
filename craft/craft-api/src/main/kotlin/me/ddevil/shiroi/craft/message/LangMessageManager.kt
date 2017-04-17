package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.message.lang.Lang
import me.ddevil.shiroi.craft.message.lang.MessageVariable
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack

interface LangMessageManager<in L : Lang<*, *>> : MessageManager {

    fun sendMessage(sender: CommandSender, message: L, vararg variables: MessageVariable)

    fun getLang(lang: L): String

    fun translateItemStack(itemStack: ItemStack, vararg variables: MessageVariable): ItemStack

    fun createItemStack(original: ItemStack,
                        displayNameLang: L,
                        lores: Set<L>,
                        vararg variables: MessageVariable): ItemStack
}