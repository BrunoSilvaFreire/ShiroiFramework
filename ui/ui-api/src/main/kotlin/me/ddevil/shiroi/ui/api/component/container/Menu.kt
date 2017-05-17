package me.ddevil.shiroi.ui.api.component.container

import me.ddevil.shiroi.ui.api.component.Drawable
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin

/**
 * A Menu is always the top-level [Container] in an UI setup. It contains the [inventory] in which the setup is being
 * displayed and every other component.
 */
interface Menu<out P : Plugin> : Container<Drawable> {

    val plugin: P

    val inventory: Inventory

    fun open(p: Player)

    fun updateIfHasViewer()

}
