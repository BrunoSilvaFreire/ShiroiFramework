package me.ddevil.shiroi.schematic

import me.ddevil.util.Serializable
import me.ddevil.util.vector.Vector3
import org.jnbt.CompoundTag

interface Schematic : Serializable {
    var origin: Vector3<Int>
    var offset: Vector3<Int>
    val length: Short
    val width: Short
    val height: Short
    val materialType: MaterialType
    val tileEntities: List<CompoundTag>
    val entities: List<CompoundTag>
    val blocks: ByteArray
    val data: ByteArray
    /**
     * Gets the block in the position
     */
    fun getBlock(x: Int, y: Int, z: Int): Byte

    fun getBlock(pos: Vector3<Int>): Byte

    fun getBlockData(x: Int, y: Int, z: Int): Byte

    fun getBlockData(pos: Vector3<Int>): Byte

    fun getIndex(x: Int, y: Int, z: Int): Int
}