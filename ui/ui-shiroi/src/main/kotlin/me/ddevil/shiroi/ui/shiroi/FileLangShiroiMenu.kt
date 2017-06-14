package me.ddevil.shiroi.ui.shiroi

import me.ddevil.shiroi.craft.config.FileConfigManager
import me.ddevil.shiroi.craft.config.FileConfigSource
import me.ddevil.shiroi.craft.config.FileConfigValue
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.shiroi.ui.api.component.container.MenuSize
import org.bukkit.inventory.ItemStack

open class FileLangShiroiMenu<P : ShiroiPlugin<*, FileConfigManager<S, *>>, S : FileConfigSource> : ShiroiMenu<P> {
    constructor(
            plugin: P,
            title: String? = null,
            size: MenuSize = MenuSize.SIX_ROWS,
            background: ItemStack? = null,
            id: String? = null
    ) : super(plugin, title, size, background, id)

    constructor(
            plugin: P,
            title: FileConfigValue<String, S>,
            size: MenuSize = MenuSize.SIX_ROWS,
            background: ItemStack? = null,
            id: String? = null
    ) : super(plugin, plugin.configManager.getValue(title), size, background, id)


}