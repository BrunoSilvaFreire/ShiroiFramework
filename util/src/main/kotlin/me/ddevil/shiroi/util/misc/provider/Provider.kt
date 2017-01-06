package me.ddevil.shiroi.util.misc.provider

interface Provider<in K, V> {

    operator fun get(key: K): V

    fun has(key: K): Boolean

    fun delete(key: K)

    fun save(value: V)
}