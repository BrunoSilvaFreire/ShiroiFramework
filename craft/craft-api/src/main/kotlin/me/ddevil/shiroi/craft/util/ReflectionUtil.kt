package me.ddevil.shiroi.craft.util

import me.ddevil.shiroi.util.misc.MinecraftVersion
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.lang.reflect.Method
import java.util.regex.Pattern

object ReflectionUtil {
    fun sendPacket(packet: Any, player: Player) {
        getSendPacketMethod(player).invoke(packet)
    }

    private val PLAYER_CONNECTION_SEND_PACKET_FIELD_NAME = "sendPacket"

    private fun getSendPacketMethod(player: Player): Method {
        return getPlayerConnection(player).javaClass.getMethod(PLAYER_CONNECTION_SEND_PACKET_FIELD_NAME)
    }

    private val PLAYER_CONNECTION_FIELD_NAME = "playerConnection"

    private fun getPlayerConnection(player: Player): Any {
        val handle = getHandle(player)
        val field = handle.javaClass.getField(PLAYER_CONNECTION_FIELD_NAME)
        field.isAccessible = true
        return field.get(handle)
    }

    val OBC_PREFIX = Bukkit.getServer()::class.java.`package`.name!!
    val NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server")
    private val VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit.", "")
    private val MATCH_VARIABLE = Pattern.compile("\\{([^}]+)}")!!
    const val PLAYER_GET_HANDLE_METHOD_NAME = "getHandle"

    fun getHandle(player: Player) = player.javaClass.getMethod(PLAYER_GET_HANDLE_METHOD_NAME).invoke(player)!!

    fun getNMSClass(className: String): Class<*> {
        val fullName = "net.minecraft.server." + getVersion() + className
        val clazz: Class<*>
        try {
            clazz = Class.forName(fullName)
        } catch (e: Exception) {
            throw e
        }

        return clazz
    }

    fun getOBCClass(className: String): Class<*> {
        val fullName = "org.bukkit.craftbukkit." + getVersion() + className
        val clazz: Class<*>
        try {
            clazz = Class.forName(fullName)
        } catch (e: Exception) {
            throw e
        }

        return clazz
    }


    val currentVersion: MinecraftVersion = loadCurrentVersion()

    private fun loadCurrentVersion(): MinecraftVersion {
        val versionName = getVersion().toUpperCase()
        return MinecraftVersion.values().firstOrNull { versionName.contains(it.name) }
                ?: MinecraftVersion.UNKNOWN
    }


    fun getVersion() = VERSION + "."

}