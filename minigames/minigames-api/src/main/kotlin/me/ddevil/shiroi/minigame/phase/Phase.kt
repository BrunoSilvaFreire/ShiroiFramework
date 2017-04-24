package me.ddevil.shiroi.minigame.phase

import me.ddevil.shiroi.minigame.feature.Feature
import me.ddevil.util.misc.Nameable

interface Phase : Nameable {
    var allowJoin: Boolean
    var allowSpectate: Boolean

    val features: Set<Feature>
    fun addFeature(feature: Feature)
    fun removeFeature(feature: Feature)

    fun <T : Feature> getFeature(type: Class<T>): T
}