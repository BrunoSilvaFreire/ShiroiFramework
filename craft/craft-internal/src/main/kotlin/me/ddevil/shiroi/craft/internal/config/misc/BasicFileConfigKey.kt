package me.ddevil.shiroi.craft.internal.config.misc

class BasicFileConfigKey @JvmOverloads constructor(
        override val name: String,
        override val resourcePath: String,
        override val folderPath: String = resourcePath) : FileConfigKey