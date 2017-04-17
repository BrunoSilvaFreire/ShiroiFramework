package me.ddevil.shiroi.craft.config

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.util.misc.Reloadable

interface ConfigManager<out K : ConfigKey> : Reloadable {

    /*val availableLoaders: Set<ConfigLoader<*, *>>

    fun registerLoader(loader: ConfigLoader<*, *>)

    fun <I, O> getLoader(inputType: Class<I>, outputType: Class<O>): ConfigLoader<I, O>*/

    val availableConfigs: Set<K>

    fun loadColorDesign(): PluginColorDesign?

}