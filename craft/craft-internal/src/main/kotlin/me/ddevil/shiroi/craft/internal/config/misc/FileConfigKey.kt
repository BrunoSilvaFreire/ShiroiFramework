package me.ddevil.shiroi.craft.internal.config.misc

import me.ddevil.shiroi.craft.config.ConfigKey

interface FileConfigKey : ConfigKey{
    val name: String
    val resourcePath: String
    val folderPath: String
}