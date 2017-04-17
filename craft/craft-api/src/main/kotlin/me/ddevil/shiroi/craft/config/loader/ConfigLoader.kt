package me.ddevil.shiroi.craft.config.loader

interface ConfigLoader<T, R> {

    val input: Class<T>

    val output: Class<R>

    fun load(type: T): R
}

