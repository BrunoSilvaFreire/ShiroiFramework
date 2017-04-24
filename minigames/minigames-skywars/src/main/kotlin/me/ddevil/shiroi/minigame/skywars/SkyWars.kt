package me.ddevil.shiroi.minigame.skywars

import me.ddevil.shiroi.craft.misc.design.PluginColorDesign
import me.ddevil.shiroi.minigame.game.Game
import me.ddevil.shiroi.minigame.game.GameConfig
import me.ddevil.shiroi.minigame.game.GameType
import me.ddevil.shiroi.minigame.match.AbstractMatch
import me.ddevil.shiroi.minigame.match.MatchResult
import me.ddevil.shiroi.minigame.misc.MatchUUIDHandler
import java.util.*

class SkyWars(
        override var colorDesign: PluginColorDesign,
        val uuidHandler: MatchUUIDHandler
) : GameType<SkyWarsGame> {

    override fun createNewGame(config: GameConfig): SkyWarsGame {
        return SkyWarsGame(this)
    }

}

class SkyWarsGame : Game<SkyWarsMatch> {

    override val gameType: SkyWars

    constructor(gameType: SkyWars) {
        this.gameType = gameType
        this.currentMatch = SkyWarsMatch(gameType.uuidHandler.generateNewMatchUUID(this))
    }

    override val currentMatch: SkyWarsMatch

}

class SkyWarsMatch(override val uuid: UUID) : AbstractMatch<SkyWarsMatch.Result>() {

    override fun toMatchResult(): Result {
        return Result()
    }

    class Result : MatchResult

}
