package me.ddevil.shiroi.craft.config.loader

abstract class AbstractConfigLoader<T, R>
constructor(
        override val input: Class<T>,
        override val output: Class<R>
) : ConfigLoader<T, R>