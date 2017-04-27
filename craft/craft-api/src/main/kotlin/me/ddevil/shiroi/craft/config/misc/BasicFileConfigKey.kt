package me.ddevil.shiroi.craft.config.misc

import me.ddevil.shiroi.craft.config.FileConfigKey

class BasicFileConfigKey @JvmOverloads constructor(
        override val name: String,
        override val resourcePath: String,
        override val folderPath: String = resourcePath) : FileConfigKey