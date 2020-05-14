package me.ddevil.shiroi.schematic

import me.ddevil.util.math.vector.Vector3

interface PlaceableSchematic : Schematic {

    fun place(x: Int, y: Int, z: Int, world: String, rotation: SchematicRotation = SchematicRotation.FORWARD)

    fun place(pos: Vector3<Int>, world: String, rotation: SchematicRotation = SchematicRotation.FORWARD)

}