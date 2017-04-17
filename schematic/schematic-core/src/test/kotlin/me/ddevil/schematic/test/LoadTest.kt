package me.ddevil.schematic.test

import me.ddevil.shiroi.schematic.SimpleSchematic
import org.json.simple.JSONObject
import org.junit.Test
import java.io.File

class LoadTest {
    @Test
    fun deserialize() {
        val file = File("C:/Work/projects/shiroi-remake/schematic/schematic-core/src/test/resources/example.schematic")
        val schematic = SimpleSchematic(file)

        println(JSONObject(schematic.serialize()).toJSONString())

    }
}