package me.ddevil.shiroi.craft.plugin

annotation class PluginSettings(val primaryAcronym: String,
                                val aliases: Array<String>,
                                val ignoreMasterConfig: Boolean = false)