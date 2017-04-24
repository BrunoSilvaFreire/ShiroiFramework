package me.ddevil.shiroi.minigame.match

import java.util.*
import kotlin.collections.HashSet

interface Match<out R : MatchResult> {

    val uuid: UUID

    val players: Set<UUID>

    fun addPlayer(player: UUID)

    fun removePlayer(player: UUID)

    fun toMatchResult(): R
}

abstract class AbstractMatch<out R : MatchResult> : Match<R> {

    private val internalPlayers: MutableSet<UUID>
    override val players: Set<UUID> get() = HashSet(internalPlayers)

    init {
        internalPlayers = HashSet()
    }


    override fun addPlayer(player: UUID) {
        internalPlayers += player
    }

    override fun removePlayer(player: UUID) {
        internalPlayers -= player
    }

}