package me.ddevil.shiroi.schematic

/**
 * Created by bruno on 09/10/2016.
 */
enum class MaterialType(val nbtName: String) {

    CLASSIC("Classic"),
    ALPHA("Alpha");

    companion object {
        fun getBySchematicName(name: String) = values().first { it.nbtName.equals(name, ignoreCase = true) }
    }
}
