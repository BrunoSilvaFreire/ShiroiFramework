package me.ddevil.shiroi.craft.config

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.util.misc.Reloadable

/**
 * Represents
 */
interface ConfigManager<out K : ConfigSource> : Reloadable {

    /*val availableLoaders: Set<ConfigLoader<*, *>>

    fun registerLoader(loader: ConfigLoader<*, *>)

    fun <I, O> getLoader(inputType: Class<I>, outputType: Class<O>): ConfigLoader<I, O>*/

    val availableConfigs: Set<K>

    fun loadColorDesign(): PluginColorDesign?

}