package me.ddevil.shiroi.craft.config.misc

import me.ddevil.json.JsonObject
import me.ddevil.json.parse.JsonParser
import me.ddevil.json.toJsonObject
import me.ddevil.shiroi.craft.util.toMap
import me.ddevil.util.emptyString
import me.ddevil.util.readToString
import org.bukkit.configuration.Configuration
import org.bukkit.configuration.file.FileConfiguration
import java.io.File
import java.io.InputStream
import java.io.Reader

class JsonConfiguration : FileConfiguration {

    constructor(defaults: Configuration?) : super(defaults)

    constructor(inputStream: InputStream) {
        loadFromString(inputStream.readBytes().readToString())
    }

    constructor(reader: Reader) {
        loadFromString(reader.readText())
    }

    constructor(file: File) {
        loadFromString(file.readText())
    }

    var host: JsonObject? = null
        private set

    override fun loadFromString(contents: String?) {
        if (contents == null) {
            return
        }
        val json = JsonParser().parseObject(contents)
        if (host == null) {
            host = json
        }
        for ((key, value) in json) {
            this[key] = value
        }
    }

    override fun saveToString(): String {
        return this.toMap().toJsonObject().toJson()
    }

    override fun buildHeader() = emptyString()

}