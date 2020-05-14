package me.ddevil.shiroi.schematic.bukkit

import me.ddevil.shiroi.schematic.MaterialType
import me.ddevil.shiroi.schematic.PlaceableSchematic
import me.ddevil.shiroi.schematic.SchematicRotation
import me.ddevil.shiroi.schematic.SimpleSchematic
import me.ddevil.util.math.vector.Vector3
import org.bukkit.Bukkit
import org.jnbt.CompoundTag
import java.io.File

class BukkitSchematic : SimpleSchematic, PlaceableSchematic {

    constructor(file: File) : super(file)

    constructor(origin: Vector3<Int>,
                offset: Vector3<Int>,
                length: Short,
                width: Short,
                height: Short,
                materialType: MaterialType,
                blocks: ByteArray,
                data: ByteArray,
                tileEntities: List<CompoundTag>,
                entities: List<CompoundTag>) : super(origin,
            offset,
            length,
            width,
            height,
            materialType,
            blocks,
            data,
            tileEntities,
            entities)

    override fun place(x: Int, y: Int, z: Int, world: String, rotation: SchematicRotation) {
        val w = Bukkit.getWorld(world) ?: throw IllegalStateException("Unknown world $world!")
        /**
         * Handle blocks
         */
        for (xi in 0 .. width) {
            for (yi in 0 .. height)
                for (zi in 0 .. length) {
                    val block = getBlock(xi, yi, zi)
                    val data = getBlockData(xi, yi, zi)
                    val pos = rotation.getPosition(x, y, z, xi, yi, zi)
                    val b = w.getBlockAt(pos.x, pos.y, pos.z)
                    b.typeId = block.toInt()
                    b.data = data
                }
        }
    }

    override fun place(pos: Vector3<Int>, world: String, rotation: SchematicRotation) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}