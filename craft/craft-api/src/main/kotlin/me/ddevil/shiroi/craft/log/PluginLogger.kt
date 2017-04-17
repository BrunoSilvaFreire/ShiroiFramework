package me.ddevil.shiroi.craft.log

interface PluginLogger {
    var defaultDebugLevel: DebugLevel
    var minimumDebugLevel: DebugLevel

    fun log(message: String, level: DebugLevel = defaultDebugLevel)

    fun printException(s: String, e: Exception)
}