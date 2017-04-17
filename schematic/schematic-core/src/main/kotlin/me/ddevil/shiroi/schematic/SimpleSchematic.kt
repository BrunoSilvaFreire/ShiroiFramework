package me.ddevil.shiroi.schematic

import com.google.common.collect.ImmutableMap
import me.ddevil.util.vector.IntVector3
import me.ddevil.util.vector.Vector3
import org.jnbt.*
import java.io.File
import java.io.FileInputStream


open class SimpleSchematic : Schematic {

    final override var origin: Vector3<Int>
    final override var offset: Vector3<Int>
    final override val length: Short
    final override val width: Short
    final override val height: Short
    final override val materialType: MaterialType
    final override val blocks: ByteArray
    final override val data: ByteArray
    final override val tileEntities: List<CompoundTag>
    final override val entities: List<CompoundTag>

    constructor(file: File) {
        try {
            /**
             * Load file and get root tag
             */
            val stream = NBTInputStream(FileInputStream(file))
            val rootTag = stream.readTag()
            if (rootTag.name != SchematicConstants.SCHEMATIC_TAG_IDENTIFIER) {
                throw IllegalStateException("Root tag is not a 'Schematic' tag!")
            }
            val root = rootTag.value as? Map<String, Tag> ?: throw IllegalStateException("Root tag contains illegal object!")

            /**
             * Dimensions and positions
             */
            this.width = root.getTag<ShortTag>(SchematicConstants.WIDTH_IDENTIFIER).value
            this.height = root.getTag<ShortTag>(SchematicConstants.HEIGHT_IDENTIFIER).value
            this.length = root.getTag<ShortTag>(SchematicConstants.LENGTH_IDENTIFIER).value
            val originX = root.getTag<IntTag>(SchematicConstants.WE_ORIGIN_X_IDENTIFIER).value
            val originY = root.getTag<IntTag>(SchematicConstants.WE_ORIGIN_Y_IDENTIFIER).value
            val originZ = root.getTag<IntTag>(SchematicConstants.WE_ORIGIN_Z_IDENTIFIER).value
            this.origin = IntVector3(originX, originY, originZ)
            val offsetX = root.getTag<IntTag>(SchematicConstants.WE_OFFSET_X_IDENTIFIER).value
            val offsetY = root.getTag<IntTag>(SchematicConstants.WE_OFFSET_Y_IDENTIFIER).value
            val offsetZ = root.getTag<IntTag>(SchematicConstants.WE_OFFSET_Z_IDENTIFIER).value
            this.offset = IntVector3(offsetX, offsetY, offsetZ)

            /**
             * Extract blocks
             */
            this.blocks = root.getTag<ByteArrayTag>(SchematicConstants.BLOCKS_IDENTIFIER).value
            this.data = root.getTag<ByteArrayTag>(SchematicConstants.DATA_IDENTIFIER).value

            val materialTypeName = root.getTag<StringTag>(SchematicConstants.MATERIALS_IDENTIFIER).value
            this.materialType = MaterialType.getBySchematicName(materialTypeName)

            /**
             * Extract entities
             */
            val tileEntitiesTags = root.getTag<ListTag>(SchematicConstants.TILE_ENTITIES_IDENTIFIER).value
            this.tileEntities = tileEntitiesTags as? List<CompoundTag> ?: throw IllegalStateException("Expected '${SchematicConstants.TILE_ENTITIES_IDENTIFIER}' objects to be of type CompoundTag, but is of illegal type!")

            val entitiesTags = root.getTag<ListTag>(SchematicConstants.ENTITIES_IDENTIFIER).value
            this.entities = entitiesTags as? List<CompoundTag> ?: throw IllegalStateException("Expected '${SchematicConstants.ENTITIES_IDENTIFIER}' objects to be of type CompoundTag, but is of illegal type!")
        } catch (e: Exception) {
            throw IllegalStateException("Couldn't load schematic from file '${file.path}'!", e)
        }
    }


    @JvmOverloads
    constructor(origin: Vector3<Int>,
                offset: Vector3<Int>,
                length: Short,
                width: Short,
                height: Short,
                materialType: MaterialType,
                blocks: ByteArray,
                data: ByteArray,
                tileEntities: List<CompoundTag> = emptyList(),
                entities: List<CompoundTag> = emptyList()) {
        this.origin = origin
        this.offset = offset
        this.length = length
        this.width = width
        this.height = height
        this.materialType = materialType
        this.blocks = blocks
        this.data = data
        this.tileEntities = tileEntities
        this.entities = entities
    }


    override fun getIndex(x: Int, y: Int, z: Int) = y * width * length + z * width + x

    override fun getBlock(x: Int, y: Int, z: Int) = blocks[getIndex(x, y, z)]

    override fun getBlock(pos: Vector3<Int>) = getBlock(pos.x, pos.y, pos.z)

    override fun getBlockData(x: Int, y: Int, z: Int) = data[getIndex(x, y, z)]

    override fun getBlockData(pos: Vector3<Int>) = getBlockData(pos.x, pos.y, pos.z)

    override fun serialize(): Map<String, Any> = ImmutableMap.Builder<String, Any>()
            .put(SchematicConstants.BLOCKS_IDENTIFIER, blocks.toList())
            .put(SchematicConstants.DATA_IDENTIFIER, data.toList())
            .put(SchematicConstants.MATERIALS_IDENTIFIER, materialType.nbtName)
            .put(SchematicConstants.HEIGHT_IDENTIFIER, height)
            .put(SchematicConstants.WIDTH_IDENTIFIER, width)
            .put(SchematicConstants.LENGTH_IDENTIFIER, length)
            .build()

    override fun toString() = "Schematic{length=$length, width=$width, height=$height, materialType=$materialType}"
}