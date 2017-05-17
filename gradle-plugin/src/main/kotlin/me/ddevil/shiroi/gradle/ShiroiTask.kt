package me.ddevil.shiroi.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.TaskAction

class ShiroiTask : DefaultTask() {
    var version: String = "3.3.0-SNAPSHOT"

    @TaskAction
    fun addModule(module: ShiroiModule) {
        project.dependencies.add(JavaPlugin.COMPILE_CONFIGURATION_NAME, createNotation(module))
    }

    private fun createNotation(module: ShiroiModule) = "me.ddevil:shiroi-framework-${module.moduleName}:$version"
}