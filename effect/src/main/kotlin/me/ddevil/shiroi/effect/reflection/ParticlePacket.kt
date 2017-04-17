/*
 *
 *  * Copyright 2015-2016 Bruno Silva Freire. All rights reserved.
 *  *
 *  *  Redistribution and use in source and binary forms, with or without modification, are
 *  *  permitted provided that the following conditions are met:
 *  *
 *  *     1. Redistributions of source code must retain the above copyright notice, this list of
 *  *        conditions and the following disclaimer.
 *  *
 *  *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *  *        of conditions and the following disclaimer in the documentation and/or other materials
 *  *        provided with the distribution.
 *  *
 *  *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *  *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 *  *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *
 *  *  The views and conclusions contained in the software and documentation are those of the
 *  *  authors and contributors and should not be interpreted as representing official policies,
 *  *  either expressed or implied, of anybody else.
 *
 */

package me.ddevil.shiroi.effect.reflection

import me.ddevil.shiroi.effect.ColorParticleEffect
import me.ddevil.shiroi.effect.ParticleEffect
import me.ddevil.shiroi.effect.ParticleFeature
import me.ddevil.shiroi.util.misc.MinecraftVersion
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

/**
 * Created by BRUNO II on 09/08/2016.
 */
class ParticlePacket(private val particle: ParticleEffect) {

    @Throws(IllegalAccessException::class, InvocationTargetException::class, InstantiationException::class)
    private fun generatePacket(x: Double,
                               y: Double,
                               z: Double,
                               offsetX: Double,
                               offsetY: Double,
                               offsetZ: Double,
                               speed: Double,
                               count: Int,
                               extra: IntArray?): Any {
        val name = particle.type.name
        if (SERVER_VERSION.newerThan(MinecraftVersion.V1_8)) {
            return PACKET_CONSTRUCTOR.newInstance(
                    ParticleHelper.getEnum(PARTICLE_ENUM_CLASS.name + "." + name),
                    true,
                    x.toFloat(),
                    y.toFloat(),
                    z.toFloat(),
                    offsetX.toFloat(),
                    offsetY.toFloat(),
                    offsetZ.toFloat(),
                    speed.toFloat(),
                    count,
                    extra)
        } else {//1.7
            return PACKET_CONSTRUCTOR.newInstance(
                    name, //Particle name
                    x.toFloat(), //X
                    y.toFloat(), //Y
                    z.toFloat(), //Z
                    offsetX.toFloat(), //X
                    offsetY.toFloat(), //Y
                    offsetZ.toFloat(), //Z
                    speed.toFloat(), //Speed
                    count
            )
        }
    }

    private fun getColor(rgbValue: Double): Double {
        var value = rgbValue
        if (value <= 0) {
            value = -1.0
        }
        return value / 255
    }

    fun send(x: Double, y: Double, z: Double, speed: Double, count: Int, players: Iterable<Player>) {
        val type = particle.type
        val packet: Any
        var offsetX = 0.0
        var offsetY = 0.0
        var offsetZ = 0.0
        var extra: IntArray? = null
        try {
            if (type.hasFeature(ParticleFeature.COLOR) && particle is ColorParticleEffect) {
                val displayColor = particle.defaultColor
                offsetX = getColor(displayColor.red.toDouble())
                offsetY = getColor(displayColor.blue.toDouble())
                offsetZ = getColor(displayColor.green.toDouble())
            }
            if (type.hasFeature(ParticleFeature.DATA)) {
                extra = IntArray(2)
            }
            packet = generatePacket(x, y, z, offsetX, offsetY, offsetZ, speed, count, extra)
        } catch (e: InstantiationException) {
            e.printStackTrace()
            return
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            return
        }

        for (p in players) {
            try {
                sendPacket(packet, p)
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
                break
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                break
            }

        }

    }

    companion object {
        private val SERVER_VERSION = ReflectionUtils.serverVersion

        private val PACKET_CONSTRUCTOR = loadConstructor()
        private val PARTICLE_ENUM_CLASS = ReflectionUtils.getNMSClass("EnumParticle")
        private val PACKET_CLASS = ReflectionUtils.getNMSClass("PacketPlayOutWorldParticles")
        private val CRAFT_ENTITY_CLASS = ReflectionUtils.getOBCClass("entity.CraftEntity")
        private val PLAYER_ENTITY_CLASS = ReflectionUtils.getNMSClass("EntityPlayer")
        private val PLAYER_CONNECTION_CLASS = ReflectionUtils.getNMSClass("PlayerConnection")
        private val GET_HANDLE_PLAYER = CRAFT_ENTITY_CLASS.getDeclaredMethod("getHandle")
        private val SEND_PACKET = PLAYER_CONNECTION_CLASS.getDeclaredMethod("sendPacket",
                ReflectionUtils.getNMSClass("Packet"))
        private val CONNECTION_FIELD = PLAYER_ENTITY_CLASS.getField("playerConnection")

        private fun loadConstructor(): Constructor<*> {
            val logger = Bukkit.getLogger()
            try {
                logger.info("Loaded Player Connection class: " + PLAYER_CONNECTION_CLASS.name)
                logger.info("Loaded Particle enum class: " + PARTICLE_ENUM_CLASS.name)
                GET_HANDLE_PLAYER.isAccessible = true
                logger.info("Loaded loadValue handle: " + GET_HANDLE_PLAYER.name)
                logger.info("Loaded packet class: " + PACKET_CLASS.name)
                CONNECTION_FIELD.isAccessible = true
                logger.info("Loaded connection field: " + CONNECTION_FIELD.name)
                logger.info("Loaded sendPacket method: " + SEND_PACKET.name)
                //Check for 1.8 particle packet
                val cons: Constructor<*>?
                logger.info("Loading for server version " + SERVER_VERSION)
                if (SERVER_VERSION.newerThan(MinecraftVersion.V1_8)) {
                    cons = PACKET_CLASS.getConstructor(
                            *arrayOf<Class<*>>(
                                    PARTICLE_ENUM_CLASS,
                                    Boolean::class.java,
                                    Float::class.java, //x
                                    Float::class.java, //y
                                    Float::class.java, //z
                                    Float::class.java, //ox
                                    Float::class.java, //oy
                                    Float::class.java, //oz
                                    Float::class.java, //speed
                                    Float::class.java, //count
                                    IntArray::class.java//data
                            )
                    )
                } else {
                    cons = PACKET_CLASS.getConstructor(*arrayOf<Class<*>>(
                            String::class.java,
                            Float::class.java,
                            Float::class.java,
                            Float::class.java,
                            Float::class.java,
                            Float::class.java,
                            Float::class.java,
                            Float::class.java,
                            Int::class.java))
                }
                if (cons != null) {
                    logger.info("Loaded packet constructor: " + cons.name)
                } else {
                    logger.info("Couldn't find constructor!")
                }
                return cons
            } catch (e: Exception) {
            }
            throw IllegalStateException("Couldn't load effect packet constructor!")
        }


        @Throws(InvocationTargetException::class, IllegalAccessException::class)
        private fun sendPacket(packet: Any, p: Player) {
            try {
                val handle = GET_HANDLE_PLAYER.invoke(p)
                val connection = CONNECTION_FIELD.get(handle)
                SEND_PACKET.invoke(connection, packet)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }
}
