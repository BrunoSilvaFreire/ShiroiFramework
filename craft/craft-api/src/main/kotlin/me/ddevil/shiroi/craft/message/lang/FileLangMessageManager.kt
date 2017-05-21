package me.ddevil.shiroi.craft.message.lang

import me.ddevil.shiroi.craft.config.FileConfigManager
import me.ddevil.shiroi.craft.config.FileConfigSource
import me.ddevil.shiroi.craft.config.FileConfigValue
import me.ddevil.shiroi.craft.message.VariableProvider
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin

open class FileLangMessageManager<
        in L : Lang<V>,
        out S : FileConfigSource,
        out V : FileConfigValue<String, S>> : AbstractLangMessageManager<L> {


    constructor(
            plugin: ShiroiPlugin<*, *>,
            messageSeparator: FileConfigValue<String, S>,
            pluginPrefix: FileConfigValue<String, S>,
            configManager: FileConfigManager<S, *>,
            providers: List<VariableProvider> = emptyList()
    ) : this(
            plugin,
            configManager.getValue(messageSeparator),
            configManager.getValue(pluginPrefix),
            configManager,
            providers
    )

    constructor(
            plugin: ShiroiPlugin<*, *>,
            messageSeparator: String,
            pluginPrefix: String,
            configManager: FileConfigManager<S, *>,
            providers: List<VariableProvider> = emptyList()
    ) : super(
            plugin,
            messageSeparator,
            pluginPrefix,
            providers
    ) {
        this.configManager = configManager
    }

    override fun getLang(lang: L) = configManager.getValue(lang.key)


    private var configManager: FileConfigManager<S, *>

}

