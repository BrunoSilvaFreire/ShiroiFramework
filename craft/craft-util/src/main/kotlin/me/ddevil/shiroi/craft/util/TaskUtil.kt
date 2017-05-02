package me.ddevil.shiroi.craft.util

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

fun createBukkitTask(action: () -> Unit) = object : BukkitRunnable() {
    override fun run() = action()
}

fun createBukkitTask(runnable: Runnable) = object : BukkitRunnable() {
    override fun run() = runnable.run()
}

private fun createAndRunBukkitTask(action: () -> Unit, starter: (BukkitRunnable) -> Unit): BukkitRunnable {
    val task = createBukkitTask(action)
    starter(task)
    return task
}

fun createAndRunBukkitTaskSync(action: () -> Unit, plugin: Plugin) = createAndRunBukkitTask(action) {
    it.runTask(plugin)
}

fun createAndRunBukkitTaskLaterSync(action: () -> Unit, plugin: Plugin, delay: Long) = createAndRunBukkitTask(action) {
    it.runTaskLater(plugin, delay)
}

@JvmOverloads
fun createAndRunBukkitTaskTimerSync(
        action: () -> Unit,
        plugin: Plugin,
        period: Long,
        delay: Long = period) = createAndRunBukkitTask(action) {
    it.runTaskTimer(plugin, delay, period)
}


fun createAndRunBukkitTaskAsync(action: () -> Unit, plugin: Plugin) = createAndRunBukkitTask(action) {
    it.runTaskAsynchronously(plugin)
}

fun createAndRunBukkitTaskLaterAsync(action: () -> Unit, plugin: Plugin, delay: Long) = createAndRunBukkitTask(action) {
    it.runTaskLaterAsynchronously(plugin, delay)
}

@JvmOverloads
fun createAndRunBukkitTaskTimerAsync(
        action: () -> Unit,
        plugin: Plugin,
        period: Long,
        delay: Long = period) = createAndRunBukkitTask(action) {
    it.runTaskTimerAsynchronously(plugin, delay, period)
}

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