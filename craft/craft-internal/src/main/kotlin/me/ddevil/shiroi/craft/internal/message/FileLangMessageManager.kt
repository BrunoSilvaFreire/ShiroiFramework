package me.ddevil.shiroi.craft.internal.message

import me.ddevil.shiroi.craft.config.FileConfigKey
import me.ddevil.shiroi.craft.config.FileConfigManager
import me.ddevil.shiroi.craft.config.FileConfigValue
import me.ddevil.shiroi.craft.message.lang.Lang
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin

open class FileLangMessageManager<
        P : ShiroiPlugin<*, FileConfigManager<K>>,
        in L : Lang<V, K>,
        K : FileConfigKey,
        out V : FileConfigValue<String, K>>
@JvmOverloads
constructor(
        plugin: P,
        messageSeparator: String,
        pluginPrefix: String,
        translators: List<TagTranslator> = emptyList()
) : AbstractLangMessageManager<P, L>(plugin, messageSeparator, pluginPrefix, translators) {

    override fun getLang(lang: L) = configManager.getValue(lang.key)


    private var configManager: FileConfigManager<K> = plugin.configManager

}

