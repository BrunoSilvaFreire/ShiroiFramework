package me.ddevil.shiroi.util.serialization

interface Deserializer<O> {
    fun deserialize(map: Map<String, Any>): O
}