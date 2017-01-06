package me.ddevil.shiroi.craft.message.lang

interface Lang<in R : LangRequest> {
    operator fun get(original: String, request: R): String

}