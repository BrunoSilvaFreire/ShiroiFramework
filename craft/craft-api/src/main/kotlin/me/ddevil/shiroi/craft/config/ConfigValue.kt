package me.ddevil.shiroi.craft.config

/**
 * Represents a value that can be extracted from a [ConfigSource]
 */
interface ConfigValue<out T, out K : ConfigSource> {
    /**
     * The source from which this value should be extracted from.
     */
    val source: K

    /**
     * The value to be used in case this value is not present in the given [source]
     */
    val defaultValue: T
}