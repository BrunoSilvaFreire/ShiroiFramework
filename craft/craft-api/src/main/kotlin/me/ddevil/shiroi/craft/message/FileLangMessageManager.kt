package me.ddevil.shiroi.craft.message

import me.ddevil.shiroi.craft.config.FileConfigManager
import me.ddevil.shiroi.craft.config.FileConfigSource
import me.ddevil.shiroi.craft.config.FileConfigValue
import me.ddevil.shiroi.craft.message.lang.Lang
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin

open class FileLangMessageManager<in L : Lang<V, K>, out K : FileConfigSource, out V : FileConfigValue<String, K>>
@JvmOverloads
constructor(
        plugin: ShiroiPlugin<*, FileConfigManager<K, *>>,
        messageSeparator: String,
        pluginPrefix: String,
        translators: List<TagTranslator> = emptyList()
) : AbstractLangMessageManager<L>(plugin, messageSeparator, pluginPrefix, translators) {

    override fun getLang(lang: L) = configManager.getValue(lang.key)


    private var configManager: FileConfigManager<K, *> = plugin.configManager

}

