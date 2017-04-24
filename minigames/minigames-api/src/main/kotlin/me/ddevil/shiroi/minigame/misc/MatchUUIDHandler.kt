package me.ddevil.shiroi.minigame.misc

import me.ddevil.shiroi.minigame.game.Game
import java.util.*

interface MatchUUIDHandler {
    fun generateNewMatchUUID(game: Game<*>): UUID
}