package me.ddevil.shiroi.util.exception

import me.ddevil.shiroi.util.misc.design.MinecraftColor

class IllegalColorException(primaryColor: MinecraftColor) : IllegalArgumentException("Invalid color detected (${primaryColor.name})!")