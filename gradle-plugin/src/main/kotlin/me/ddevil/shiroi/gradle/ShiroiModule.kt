package me.ddevil.shiroi.gradle

enum class ShiroiModule(
        val moduleName: String
) {
    UTIL("util"),
    UI("ui"),
    UI_SHIROI("shiroi-ui"),
    CRAFT_UTIL("craft-util"),
    CRAFT_AP("craft-api"),
    SCHEMATIC("schematic-core"),
    SCHEMATIC_BUKKIT("schematic-bukkit"),
    MINIGAMES("minigames-api");

}