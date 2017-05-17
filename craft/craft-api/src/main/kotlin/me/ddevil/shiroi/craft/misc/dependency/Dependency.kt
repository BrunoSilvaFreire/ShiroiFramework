package me.ddevil.shiroi.craft.misc.dependency

import me.ddevil.util.misc.AbstractNameable
import java.net.URL

abstract class Dependency constructor(
        val downloadUrl: URL,
        val version: String,
        name: String,
        alias: String
) : AbstractNameable(name, alias) {

    override val fullName: String
        get() = "$name($alias/$version)"

}