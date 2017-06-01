package me.ddevil.shiroi.craft.util

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

fun createBukkitTask(action: () -> Unit) = object : BukkitRunnable() {
    override fun run() = action()
}

fun createBukkitTask(runnable: Runnable) = object : BukkitRunnable() {
    override fun run() = runnable.run()
}

private fun createAndRunBukkitTask(starter: (BukkitRunnable) -> Unit,
                                   action: () -> Unit): BukkitRunnable {
    val task = createBukkitTask(action)
    starter(task)
    return task
}

fun createAndRunBukkitTaskSync(plugin: Plugin, action: () -> Unit) = createAndRunBukkitTask({
    it.runTask(plugin)
}, action)

fun createAndRunBukkitTaskLaterSync(plugin: Plugin, delay: Long, action: () -> Unit) = createAndRunBukkitTask({
    it.runTaskLater(plugin, delay)
}, action)

@JvmOverloads
fun createAndRunBukkitTaskTimerSync(
        action: () -> Unit,
        plugin: Plugin,
        period: Long,
        delay: Long = period) = createAndRunBukkitTask({
    it.runTaskTimer(plugin, delay, period)
}, action)


fun createAndRunBukkitTaskAsync(action: () -> Unit, plugin: Plugin) = createAndRunBukkitTask({
    it.runTaskAsynchronously(plugin)
}, action)

fun createAndRunBukkitTaskLaterAsync(action: () -> Unit, plugin: Plugin, delay: Long) = createAndRunBukkitTask({
    it.runTaskLaterAsynchronously(plugin, delay)
}, action)

@JvmOverloads
fun createAndRunBukkitTaskTimerAsync(
        action: () -> Unit,
        plugin: Plugin,
        period: Long,
        delay: Long = period) = createAndRunBukkitTask({
    it.runTaskTimerAsynchronously(plugin, delay, period)
}, action)

private fun createAndRunBukkitTask(runnable: Runnable, starter: (BukkitRunnable) -> Unit): BukkitRunnable {
    val task = createBukkitTask(runnable)
    starter(task)
    return task
}

fun createAndRunBukkitTaskSync(runnable: Runnable, plugin: Plugin) = createAndRunBukkitTask(runnable) {
    it.runTask(plugin)
}

fun createAndRunBukkitTaskLaterSync(
        runnable: Runnable,
        plugin: Plugin,
        delay: Long) = createAndRunBukkitTask(runnable) {
    it.runTaskLater(plugin, delay)
}

@JvmOverloads
fun createAndRunBukkitTaskTimerSync(
        runnable: Runnable,
        plugin: Plugin,
        period: Long,
        delay: Long = period) = createAndRunBukkitTask(runnable) {
    it.runTaskTimer(plugin, delay, period)
}


fun createAndRunBukkitTaskAsync(runnable: Runnable, plugin: Plugin) = createAndRunBukkitTask(runnable) {
    it.runTaskAsynchronously(plugin)
}

fun createAndRunBukkitTaskLaterAsync(
        runnable: Runnable,
        plugin: Plugin,
        delay: Long) = createAndRunBukkitTask(runnable) {
    it.runTaskLaterAsynchronously(plugin, delay)
}

@JvmOverloads
fun createAndRunBukkitTaskTimerAsync(
        runnable: Runnable,
        plugin: Plugin,
        period: Long,
        delay: Long = period) = createAndRunBukkitTask(runnable) {
    it.runTaskTimerAsynchronously(plugin, delay, period)
}