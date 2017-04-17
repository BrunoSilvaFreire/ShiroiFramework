package me.ddevil.shiroi.effect

import me.ddevil.shiroi.effect.reflection.ParticlePacket
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.entity.Player

class ColorParticleEffect(
        type: ParticleType,
        var defaultColor: Color
) : ParticleEffect(type) {

    @JvmOverloads
    fun display(location: Location, player: Player, color: Color = defaultColor, speed: Double = 1.0, count: Int = 1) {
        display(location, setOf(player), color, speed, count)
    }

    @JvmOverloads
    fun display(x: Double,
                y: Double,
                z: Double,
                player: Player,
                color: Color = defaultColor,
                speed: Double = 1.0,
                count: Int = 1) {
        display(x, y, z, setOf(player), color, speed, count)
    }

    @JvmOverloads
    fun display(location: Location,
                players: Iterable<Player> = Bukkit.getOnlinePlayers(),
                color: Color = defaultColor,
                speed: Double = 1.0,
                count: Int = 1) {
        display(location.x, location.y, location.z, players, color, speed, count)
    }

    @JvmOverloads
    fun display(x: Double,
                y: Double,
                z: Double,
                players: Iterable<Player> = Bukkit.getOnlinePlayers(),
                color: Color = defaultColor,
                speed: Double = 1.0,
                count: Int = 1) {
        if (!type.hasFeature(ParticleFeature.COLOR)) {
            throw IllegalStateException("This particle type does not support colors!")
        }
        packet.send(x, y, z, speed, count, players)
    }
}

open class ParticleEffect(var type: ParticleType) {

    val packet = ParticlePacket(this)

    @JvmOverloads
    fun display(location: Location, player: Player, speed: Double = 1.0, count: Int = 1) {
        display(location, setOf(player), speed, count)
    }

    @JvmOverloads
    fun display(x: Double, y: Double, z: Double, player: Player, speed: Double = 1.0, count: Int = 1) {
        display(x, y, z, setOf(player), speed, count)
    }

    @JvmOverloads
    fun display(location: Location,
                players: Iterable<Player> = Bukkit.getOnlinePlayers(),
                speed: Double = 1.0,
                count: Int = 1) {
        display(location.x, location.y, location.z, players, speed, count)
    }

    @JvmOverloads
    fun display(x: Double,
                y: Double,
                z: Double,
                players: Iterable<Player> = Bukkit.getOnlinePlayers(),
                speed: Double = 1.0,
                count: Int = 1) {
        packet.send(x, y, z, speed, count, players)
    }

}

