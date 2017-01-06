package me.ddevil.shiroi.util.serialization

interface Serializer<T> {
    fun serialize(value: T): Map<String, Any>

}