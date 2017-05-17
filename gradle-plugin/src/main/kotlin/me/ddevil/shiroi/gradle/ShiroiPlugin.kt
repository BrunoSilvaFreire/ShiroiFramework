package me.ddevil.shiroi.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class ShiroiPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        p0.plugins.apply(JavaPlugin::class.java)
        p0.tasks.add(ShiroiTask())
    }

}

