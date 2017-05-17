package me.ddevil.shiroi.minigame.feature

import me.ddevil.util.misc.AbstractNameableDescribable
import me.ddevil.util.misc.Describable
import me.ddevil.util.misc.Nameable
import org.bukkit.event.Listener

interface Feature : Nameable, Describable, Listener

abstract class AbstractFeature(
        name: String,
        alias: String,
        description: List<String>
) : AbstractNameableDescribable(
        name,
        alias,
        description
), Feature