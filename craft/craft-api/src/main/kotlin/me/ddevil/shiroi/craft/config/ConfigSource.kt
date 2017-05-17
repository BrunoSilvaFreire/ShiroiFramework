package me.ddevil.shiroi.craft.config

/**
 * Represents a file or any kind of object where [ConfigValue]s can be extracted from.
 */
interface ConfigSource {
    /**
     * The name from which this ConfigSource can be referred to, for example:
     * A config file called "mainConfig.yml", should have it's name set to as "mainConfig"
     */
    val name: String
}