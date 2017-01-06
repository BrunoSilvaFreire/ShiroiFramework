package me.ddevil.shiroi.util.serialization

interface Serializable {

    fun serialize(): Map<String, Any>

}