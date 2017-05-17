package me.ddevil.shiroi.craft.misc.dependency

import me.ddevil.shiroi.craft.log.DebugLevel
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import java.io.File
import java.io.FileOutputStream
import java.nio.channels.Channels
import java.util.*
import java.util.jar.JarFile


class SimpleDependencyManager
@JvmOverloads
constructor(
        val plugin: ShiroiPlugin<*, *>,
        val dependenciesFolder: File = File(plugin.dataFolder, SimpleDependencyManager.DEFAULT_DEPENDENCIES_FOLDER_NAME)
) : DependencyManager {
    companion object {
        const val DEFAULT_DEPENDENCIES_FOLDER_NAME = "libs"
    }

    init {
        dependenciesFolder.mkdirs()
    }

    private val loadedDependencies: MutableMap<String, JarFile> = HashMap()

    override fun loadOrDownload(dependency: Dependency) {
        loadedDependencies.getOrPut(
                dependency.name
        ) {
            //Download
            val file = File(dependenciesFolder, "${dependency.name}.jar")
            plugin.pluginLogger.log("Loading dependency '${dependency.fullName}'...",
                    DebugLevel.OKAY_SOME_REAL_SHIT_HAPPENED)

            if (!file.exists()) {
                downloadDependency(dependency, file)
            }

            val jarFile = JarFile(file)
            plugin.pluginLogger.log("Loading dependency jar '${file.name}'...", DebugLevel.OKAY_SOME_REAL_SHIT_HAPPENED)
            val loader = plugin.javaClass.classLoader
            for (entry in jarFile.entries()) {
                val name = entry.name
                if (name.endsWith(".class")) {
                    val className = entry.name.replace('/', '.').removeSuffix(".class")
                    loader.loadClass(className)
                }
            }
            return@getOrPut jarFile
        }

    }

    private fun downloadDependency(dependency: Dependency, file: File) {
        val url = dependency.downloadUrl
        plugin.pluginLogger.log("Downloading dependency '${dependency.fullName}'... ($url -> ${file.path})",
                DebugLevel.OKAY_SOME_REAL_SHIT_HAPPENED)
        val rbc = Channels.newChannel(url.openStream())
        val fos = FileOutputStream(file)
        fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
    }

    override fun isPresent(dependency: Dependency) = loadedDependencies.containsKey(dependency.name)

}