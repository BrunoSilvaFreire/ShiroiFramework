package me.ddevil.shiroi.craft.command

import me.ddevil.shiroi.craft.plugin.ShiroiPlugin

open class ShiroiCommand<out P : ShiroiPlugin<*, *>>(val plugin: P)
