package me.ddevil.shiroi.minigame.game

import me.ddevil.util.getNumber
import me.ddevil.util.getOrException
import me.ddevil.util.getOrNull

class GameConfig {
    val minPlayers: Int
    val maxPlayers: Int
    private val internalProperties: Map<String, Any>

    val properties: Map<String, Any> get() = HashMap(internalProperties)

    constructor(minPlayers: Int, maxPlayers: Int) {
        this.minPlayers = minPlayers
        this.maxPlayers = maxPlayers
        this.internalProperties = HashMap()
    }

    fun getString(key: String): String = internalProperties.getOrException(key)

    fun getNumber(key: String): Number = internalProperties.getOrException(key)

    fun getFloat(key: String) = internalProperties.getNumber(key).toFloat()

    fun getDouble(key: String) = internalProperties.getNumber(key).toDouble()

    fun getInt(key: String) = internalProperties.getNumber(key).toInt()

    fun getLong(key: String) = internalProperties.getNumber(key).toLong()

    fun <V> getMap(key: String): Map<String, V> = internalProperties.getOrException(key)

    fun getMapAny(key: String): Map<String, Any> = getMap(key)

    fun getBoolean(key: String): Boolean = internalProperties.getOrException(key)

    fun <T> getList(key: String): List<T> = internalProperties.getOrException(key)

    fun getStringOrNull(key: String): String? = internalProperties.getOrNull(key)

    fun getNumberOrNull(key: String): Number? = internalProperties.getOrNull(key)

    fun getFloatOrNull(key: String): Float? = internalProperties.getNumber(key).toFloat()

    fun getDoubleOrNull(key: String): Double? = internalProperties.getNumber(key).toDouble()

    fun getIntOrNull(key: String): Int? = internalProperties.getNumber(key).toInt()

    fun getLongOrNull(key: String): Long? = internalProperties.getNumber(key).toLong()

    fun <V> getMapOrNull(key: String): Map<String, V>? = internalProperties.getOrNull(key)

    fun getMapAnyOrNull(key: String): Map<String, Any>? = getMap(key)

    fun getBooleanOrNull(key: String): Boolean? = internalProperties.getOrNull(key)

    fun <T> getListOrNull(key: String): List<T>? = internalProperties.getOrNull(key)

}