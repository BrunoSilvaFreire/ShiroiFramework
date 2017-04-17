package me.ddevil.shiroi.minigame

import org.bukkit.entity.Player

interface GameMatch {

    val players: Set<Player>

    val spectators: Set<Player>

    val finished: Boolean
}