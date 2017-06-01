package me.ddevil.shiroi.craft.misc.task

import co.aikar.taskchain.BukkitTaskChainFactory
import co.aikar.taskchain.TaskChain
import co.aikar.taskchain.TaskChainFactory
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.shiroi.util.misc.Toggleable

class ChainFactory(val plugin: ShiroiPlugin<*, *>) : Toggleable {

    override fun enable() {
        taskChainFactory = BukkitTaskChainFactory.create(plugin)
    }

    override fun disable() {
    }

    lateinit var taskChainFactory: TaskChainFactory
        private set

    fun <T> newChain(): TaskChain<T> {
        return taskChainFactory.newChain()
    }

    fun <T> newSharedChain(name: String): TaskChain<T> {
        return taskChainFactory.newSharedChain(name)
    }
}