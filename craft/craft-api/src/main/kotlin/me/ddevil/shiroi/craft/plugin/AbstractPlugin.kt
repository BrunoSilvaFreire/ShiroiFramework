package me.ddevil.shiroi.craft.plugin

import me.ddevil.shiroi.craft.command.CommandManager
import me.ddevil.shiroi.craft.command.SimpleCommandManager
import me.ddevil.shiroi.craft.config.ConfigManager
import me.ddevil.shiroi.craft.log.DebugLevel
import me.ddevil.shiroi.craft.log.InternalPluginLogger
import me.ddevil.shiroi.craft.log.PluginLogger
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.misc.master.MasterConfig
import me.ddevil.shiroi.craft.misc.task.ChainFactory
import me.ddevil.shiroi.craft.misc.variable.VariableManager
import me.ddevil.shiroi.craft.util.set
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.logging.Level
import kotlin.properties.Delegates


abstract class AbstractPlugin<M : MessageManager, C : ConfigManager<*>> : JavaPlugin(), ShiroiPlugin<M, C> {

    final override val settings: PluginSettings
    final override val chainFactory = ChainFactory(this)
    final override val masterConfig: MasterConfig
    final override val pluginLogger: PluginLogger
    final override val variableManager: VariableManager

    final override var colorDesign: PluginColorDesign by Delegates.notNull<PluginColorDesign>()

    final lateinit override var messageManager: M
        private set

    final lateinit override var configManager: C
        private set

    final lateinit override var commandManager: CommandManager
        private set


    override val allKnownAliases: Array<String>
        get() = arrayOf(*settings.aliases, settings.primaryAcronym)


    init {
        if (javaClass.isAnnotationPresent(PluginSettings::class.java)) {
            this.settings = javaClass.getAnnotation(PluginSettings::class.java)
        } else {
            throw IllegalStateException("Plugin author didn't specify PluginSettings in the main class!")
        }
        this.pluginLogger = InternalPluginLogger(this)
        /*
         * Load master config
         */
        val pluginsFolder = dataFolder.parentFile
        val shiroiFolder = File(pluginsFolder, "Shiroi")
        if (!shiroiFolder.exists()) {
            shiroiFolder.mkdir()
        }
        val masterConfigFile = File(shiroiFolder, "master.yml")
        if (!masterConfigFile.exists()) {
            pluginLogger.log("Couldn't find master config file, creating new one...")
            this.masterConfig = MasterConfig()
            val config = YamlConfiguration()
            config.set(masterConfig.serialize())
            config.save(masterConfigFile)
        } else {
            this.masterConfig = MasterConfig(YamlConfiguration.loadConfiguration(masterConfigFile))
        }
        this.variableManager = VariableManager()
    }


    final override fun onEnable() {
        /*
         * Load prefix
         */
        pluginLogger.log("Loading prefix...")
        val start = System.currentTimeMillis()
        chainFactory.enable()
        /*
         * First of all we load the config manager, as it is the primary
         * source for resources used by the prefix.
         * Then, we use it to load the prefix's color design
         */
        this.configManager = loadConfigManager()
        configManager.enable()
        pluginLogger.log("Loaded Config Manager...")

        /*
         * Handle color design
         * 1 - Check for master color design
         * 2 - Try to load from the config Manager
         * 3 - If #2 fails, load default
         */

        colorDesign = tryLoadColorDesign()
        pluginLogger.log("Loaded Color Design... ($colorDesign)")

        /*
         * Now we load the message manager and command manager, as the
         * color design is loaded and it's safe to translate it.
         */
        this.messageManager = loadMessageManager()
        messageManager.enable()
        pluginLogger.log("Loaded Message Manager...")
        this.commandManager = SimpleCommandManager(this)
        commandManager.enable()
        pluginLogger.log("Loaded Command Manager...")
        onEnable0()
        val end = System.currentTimeMillis()
        val total = end - start
        pluginLogger.log("Plugin loaded in $total ms! (${(total / 1000).toDouble()}s)")
    }


    private fun tryLoadColorDesign(): PluginColorDesign {
        if (masterConfig.useMasterColor && !settings.ignoreMasterConfig) {
            pluginLogger.log("Using Shiroi master color design from the master config as color design")
            return masterConfig.masterColor
        }
        val tempColorDesign = configManager.loadColorDesign()
        if (tempColorDesign != null) {
            return tempColorDesign
        } else {
            pluginLogger.log("Plugin returned a null color design! Using the Shiroi default color design, notify the prefix author!",
                    DebugLevel.FUCK_MAN_SOUND_THE_ALARMS)
            return PluginColorDesign.SHIROI_COLOR_DESIGN
        }
    }

    abstract fun loadConfigManager(): C
    abstract fun loadMessageManager(): M

    override fun enable() {

    }

    override fun disable() {
    }

    override fun saveResource(resourcePath: String, destination: File) {
        var path = resourcePath
        if (path == "") {
            throw IllegalArgumentException("ResourcePath cannot be null or empty")
        }

        path = path.replace('\\', '/')
        val `in` = getResource(path) ?: throw IllegalArgumentException("The embedded resource '$path' cannot be found in $file")

        val lastIndex = path.lastIndexOf('/')
        val outDir = File(dataFolder, path.substring(0, if (lastIndex >= 0) lastIndex else 0))

        if (!outDir.exists()) {
            outDir.mkdirs()
        }

        try {
            if (destination.exists()) {
                destination.delete()
            }
            val out = FileOutputStream(destination)
            val buf = ByteArray(1024)
            var len = `in`.read(buf)
            while (len > 0) {
                out.write(buf, 0, len)
                len = `in`.read(buf)
            }
            out.close()
            `in`.close()

        } catch (ex: IOException) {
            logger.log(Level.SEVERE, "Could not save " + destination.name + " to " + destination, ex)
        }

    }

    override fun registerListener(listener: Listener) = Bukkit.getPluginManager().registerEvents(listener, this)

    override fun unregisterListener(listener: Listener) = HandlerList.unregisterAll(listener)

    override fun reload(sender: CommandSender) {
        messageManager.sendMessage(sender, "Reloading ConfigManager...")
        configManager.reload(sender)
        colorDesign = tryLoadColorDesign()
        messageManager.sendMessage(sender, "Using ColorDesign: ${colorDesign.demonstration}")
        messageManager.sendMessage(sender, "Reloading MessageManager...")
        messageManager.reload(sender)
        messageManager.sendMessage(sender, "Reloading CommandManager...")
        commandManager.reload(sender)
    }

    override fun reload() {
        /*
         * Follow the same steps as on enable
         */
        configManager.reload()
        colorDesign = tryLoadColorDesign()
        messageManager.reload()
        commandManager.reload()
    }

    abstract fun onEnable0()

}