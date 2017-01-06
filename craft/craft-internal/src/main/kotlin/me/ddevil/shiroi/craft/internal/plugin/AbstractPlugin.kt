package me.ddevil.shiroi.craft.internal.plugin

import me.ddevil.shiroi.craft.command.CommandManager
import me.ddevil.shiroi.craft.config.ConfigManager
import me.ddevil.shiroi.craft.message.MessageManager
import me.ddevil.shiroi.craft.misc.PluginSettings
import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.craft.misc.task.ChainFactory
import me.ddevil.shiroi.craft.plugin.ShiroiPlugin
import me.ddevil.shiroi.util.misc.Toggleable
import org.bukkit.plugin.java.JavaPlugin
import kotlin.properties.Delegates

abstract class AbstractPlugin<out M : MessageManager, out C : ConfigManager<*>>() : JavaPlugin(), ShiroiPlugin<M, C> {

    final override val settings: PluginSettings
    final override val chainFactory: ChainFactory

    /*
    * Implement properties and delegate to internal functions
    */
    final override var colorDesign: PluginColorDesign by Delegates.notNull<PluginColorDesign>()
    final override val messageManager: M
        get() = internalMessageManager
    final override val commandManager: CommandManager
        get() = internalCommandManager
    final override val configManager: C
        get() = internalConfigManager


    /*
    * Use some kotlin magic and delegate these properties, as they are initialized
    * on #onEnable to keep things safe
    */
    private var internalMessageManager: M by Delegates.notNull<M>()
    private var internalCommandManager: CommandManager by Delegates.notNull<CommandManager>()
    private var internalConfigManager: C by Delegates.notNull<C>()

    init {
        if (javaClass.isAnnotationPresent(PluginSettings::class.java)) {
            this.settings = javaClass.getAnnotation(PluginSettings::class.java)
        } else {
            throw IllegalStateException("Plugin author didn't specify PluginSettings in the main class!")
        }
        chainFactory = ChainFactory(this)
    }


    final override fun onEnable() {
        /*
         * Load plugin
         */
        pluginLogger.log("Loading plugin...")
        val start = System.currentTimeMillis()
        /*
         * First of all we load the config manager, as it is the primary
         * source for resources used by the plugin.
         * Then, we use it to load the plugin's color design
         */
        this.internalConfigManager = loadConfigManager().enable()
        pluginLogger.log("Loaded Config Manager...")

        /*
         * Handle color design
         * 1 - Check for master color design
         * 2 - Try to load from the config Manager
         * 3 - If #2 fails, load default
         */

        /*
         * TODO: Implement main color design loading
         */
        colorDesign = tryLoadColorDesign()
        pluginLogger.log("Loaded Color Design... (${colorDesign.primaryColor.name}, ${colorDesign.secondaryColor.name}, ${colorDesign.neutralColor.name}, ${colorDesign.warningColor.name})")

        /*
         * Now we load the message manager and command manager, as the
         * color design is loaded and it's safe to get it.
         */
        this.internalMessageManager = loadMessageManager().enable()
        pluginLogger.log("Loaded Message Manager...")
        this.internalCommandManager = loadCommandManager().enable()
        pluginLogger.log("Loaded Command Manager...")

        val end = System.currentTimeMillis()
        val total = end - start
        pluginLogger.log("Plugin loaded in $total ms! (${(total / 1000).toDouble()}s)")
    }

    private fun tryLoadColorDesign(): PluginColorDesign {
        val tempColorDesign = configManager.loadColorDesign()
        if (tempColorDesign != null) {
            return tempColorDesign
        } else {
            pluginLogger.log("")
            return PluginColorDesign.SHIROI_COLOR_DESIGN
        }
    }

    abstract fun loadConfigManager(): C
    abstract fun loadCommandManager(): CommandManager
    abstract fun loadMessageManager(): M

    override fun <T : Toggleable> T.enable() = this
    override fun <T : Toggleable> T.disable() = this

    override fun reload() {
        /*
         * Follow the same steps as on enable
         */
        configManager.reload()
        colorDesign = tryLoadColorDesign()
        messageManager.reload()
        commandManager.reload()
    }
}