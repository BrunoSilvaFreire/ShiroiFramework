package me.ddevil.shiroi.util.test

import me.ddevil.shiroi.util.misc.item.Item
import me.ddevil.shiroi.util.misc.item.Material
import me.ddevil.shiroi.util.misc.item.enchantment.Enchantment
import org.junit.Test

class ItemTest {
    @Test
    fun test() {
        val item = Item(Material.DIAMOND_SWORD)

        item.addEnchantment(Enchantment.OXYGEN)
        item.addEnchantment(Enchantment.THORNS)
    }
}