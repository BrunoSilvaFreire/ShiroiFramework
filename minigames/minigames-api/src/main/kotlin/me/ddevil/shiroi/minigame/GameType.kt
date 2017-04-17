package me.ddevil.shiroi.minigame

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.minigame.phase.Phase

interface GameType<out M : GameMatch> {

    val colorDesign: PluginColorDesign
    val currentPhase: Phase
    val currentMatch: M

}


