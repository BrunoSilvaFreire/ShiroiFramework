package me.ddevil.shiroi.craft.log

interface PluginLogger {
    val defaultDebugLevel: DebugLevel
    fun log(message: String, level: DebugLevel = defaultDebugLevel)

}