package me.ddevil.shiroi.effect.shape

import org.bukkit.Location

interface ParticleShape {

    fun calculatePositions(center: Location, resolution: ShapeResolution): Iterable<Location>
}

