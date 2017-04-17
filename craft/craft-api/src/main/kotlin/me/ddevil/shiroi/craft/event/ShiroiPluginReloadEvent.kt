package me.ddevil.shiroi.craft.event

import me.ddevil.shiroi.craft.plugin.ShiroiPlugin

class ShiroiPluginReloadEvent(val plugin: ShiroiPlugin<*, *>) : ShiroiEvent()

