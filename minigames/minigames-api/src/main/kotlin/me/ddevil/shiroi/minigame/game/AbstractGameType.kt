package me.ddevil.shiroi.minigame.game

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign

abstract class AbstractGameType<out G : Game<*>>(
        override var colorDesign: PluginColorDesign
) : GameType<G>