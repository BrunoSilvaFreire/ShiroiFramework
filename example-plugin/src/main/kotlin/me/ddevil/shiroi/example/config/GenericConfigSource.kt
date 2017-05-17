package me.ddevil.shiroi.example.config

import me.ddevil.shiroi.craft.config.FileConfigSource

enum class GenericConfigSource(override val resourcePath: String, override val folderPath: String) : FileConfigSource {
    MAIN_CONFIG("config.yml", "config.yml"),
    STORAGE_CONFIG("storage.yml", "storage/storage.yml"),
    MESSAGES_CONFIG("lang.yml", "lang.yml");

}
