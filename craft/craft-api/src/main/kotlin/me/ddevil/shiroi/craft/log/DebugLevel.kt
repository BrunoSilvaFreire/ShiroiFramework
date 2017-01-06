package me.ddevil.shiroi.craft.log

import java.util.logging.Level

/**
 * How much of a fuck to give when something goes wrong.
 */
enum class DebugLevel constructor(val javaLevel: Level) {
    MEH(Level.INFO),
    NO_BIG_DEAL(Level.INFO),
    SHOULDNT_HAPPEN_BUT_WE_CAN_HANDLE_IT(Level.WARNING),
    OKAY_SOME_REAL_SHIT_HAPPENED(Level.WARNING),
    FUCK_MAN_SOUND_THE_ALARMS(Level.SEVERE);

    fun isHigherThan(level: DebugLevel): Boolean {
        return ordinal >= level.ordinal
    }
}