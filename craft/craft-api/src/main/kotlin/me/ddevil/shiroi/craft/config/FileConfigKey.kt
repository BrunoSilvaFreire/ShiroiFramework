package me.ddevil.shiroi.craft.config

interface FileConfigKey : ConfigKey {
    val name: String
    val resourcePath: String
    val folderPath: String
}