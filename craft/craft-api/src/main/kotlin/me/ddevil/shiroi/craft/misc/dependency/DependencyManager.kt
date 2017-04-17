package me.ddevil.shiroi.craft.misc.dependency


interface DependencyManager {

    fun loadOrDownload(dependency: Dependency)

    fun isPresent(dependency: Dependency): Boolean
}

