package me.ddevil.shiroi.craft.config.misc

import me.ddevil.shiroi.craft.config.FileConfigSource

class BasicFileConfigSource
@JvmOverloads
constructor(
        override val name: String,
        override val resourcePath: String,
        override val folderPath: String = resourcePath
) : FileConfigSource