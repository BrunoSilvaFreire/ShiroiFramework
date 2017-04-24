package me.ddevil.shiroi.minigame.game

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign

interface GameType<out G : Game<*>> {

    var colorDesign: PluginColorDesign

    fun createNewGame(config: GameConfig): G

}

