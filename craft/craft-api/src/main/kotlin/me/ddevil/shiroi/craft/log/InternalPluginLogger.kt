package me.ddevil.shiroi.craft.log

import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import java.util.logging.Logger

class InternalPluginLogger
@JvmOverloads constructor(
        val plugin: ShiroiPlugin<*, *>,
        override var defaultDebugLevel: DebugLevel = DebugLevel.NO_BIG_DEAL,
        override var minimumDebugLevel: DebugLevel = DebugLevel.NO_BIG_DEAL) : PluginLogger {

    override fun printException(s: String, e: Exception) {
        log(s)
        val message = e.message
        if (message != null) {
            log(message)
        }
        log("<-- Error -->")
        e.printStackTrace()
        log("<-- Error -->")
    }

    private var logger: Logger = plugin.logger

    override fun log(message: String, level: DebugLevel) {
        if (level.isHigherThan(minimumDebugLevel)) {
            logger.log(level.javaLevel, message)
        }
    }


}