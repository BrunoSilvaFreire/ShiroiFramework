package me.ddevil.shiroi.craft.config

/**
 * Represents a [ConfigSource] in form of a file, usually in the YAML format, but can be used in other formats,
 * like JSON for example.
 */
interface FileConfigSource : ConfigSource {
    val resourcePath: String
    val folderPath: String
}