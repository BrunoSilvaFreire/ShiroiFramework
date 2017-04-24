package me.ddevil.shiroi.minigame.game

import me.ddevil.shiroi.minigame.match.Match

interface Game<out M : Match<*>> {

    val gameType: GameType<*>

    val currentMatch: M

}

