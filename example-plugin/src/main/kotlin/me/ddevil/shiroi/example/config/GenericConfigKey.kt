package me.ddevil.shiroi.example.config

import me.ddevil.shiroi.craft.config.FileConfigKey

enum class GenericConfigKey(override val resourcePath: String, override val folderPath: String) : FileConfigKey {
    MAIN_CONFIG("config.yml", "config.yml"),
    STORAGE_CONFIG("storage.yml", "storage/storage.yml"),
    MESSAGES_CONFIG("lang.yml", "lang.yml");

}
