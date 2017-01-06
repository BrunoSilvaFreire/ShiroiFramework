package me.ddevil.shiroi.craft.config

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.util.misc.Reloadable

interface ConfigManager<K : ConfigKey> : Reloadable {

    fun <T> getValue(value: ConfigValue<T, K>): T

    val availableConfigs: Set<K>

    fun loadColorDesign(): PluginColorDesign?

}