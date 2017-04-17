package me.ddevil.shiroi.schematic

import me.ddevil.shiroi.schematic.exceptions.IllegalTagException
import org.jnbt.ByteArrayTag
import org.jnbt.Tag


inline fun <reified T : Tag> Map<String, Tag>.getTag(tagName: String): T {
    val stag = this[tagName]
    return (stag ?: throw IllegalStateException("Couldn't find '$tagName' tagName in schematic!"))
            as? T ?: throw IllegalTagException(stag, ByteArrayTag::class.java)
}
