package me.ddevil.shiroi.util.misc

enum class MinecraftVersion constructor(val versionNumber: Int) {
    UNKNOWN(-1),
    V1_7(107),
    V1_8(108),
    V1_9(109),
    V1_10(110),
    V1_11(111);


    fun olderThan(version: MinecraftVersion): Boolean {
        return version.versionNumber >= this.versionNumber
    }

    fun newerThan(version: MinecraftVersion): Boolean {
        return version.versionNumber <= this.versionNumber
    }

    fun isBetween(oldVersion: MinecraftVersion, newVersion: MinecraftVersion): Boolean {
        return newerThan(oldVersion) && olderThan(newVersion)
    }
}
