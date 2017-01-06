package me.ddevil.shiroi.craft.misc.task

import co.aikar.taskchain.BukkitTaskChainFactory
import co.aikar.taskchain.TaskChain
import co.aikar.taskchain.TaskChainFactory
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin

class ChainFactory(plugin: ShiroiPlugin<*, *>) {
    val taskChainFactory: TaskChainFactory = BukkitTaskChainFactory.create(plugin)

    fun <T> newChain(): TaskChain<T> {
        return taskChainFactory.newChain()
    }

    fun <T> newSharedChain(name: String): TaskChain<T> {
        return taskChainFactory.newSharedChain(name)
    }
}