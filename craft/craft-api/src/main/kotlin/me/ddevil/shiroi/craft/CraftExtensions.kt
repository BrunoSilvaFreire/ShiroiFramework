package me.ddevil.shiroi.craft

import me.ddevil.shiroi.util.misc.item.Item
import me.ddevil.shiroi.util.misc.item.ItemStack
import me.ddevil.shiroi.util.misc.item.Material
import org.bukkit.event.block.Action


fun Action.isLeftClick() = this == Action.LEFT_CLICK_AIR || this == Action.LEFT_CLICK_BLOCK

fun Action.isRightClick() = this == Action.RIGHT_CLICK_AIR || this == Action.RIGHT_CLICK_BLOCK

fun Action.isPhysical() = this == Action.PHYSICAL

fun Action.isBlockClick() = this == Action.RIGHT_CLICK_BLOCK || this == Action.LEFT_CLICK_BLOCK

fun Action.isAirClick() = this == Action.RIGHT_CLICK_AIR || this == Action.LEFT_CLICK_AIR

@JvmOverloads
fun Item.toBukkit(quantity: Int = 1) = org.bukkit.inventory.ItemStack(material.id, quantity, data.toShort())

fun ItemStack.toBukkit() = org.bukkit.inventory.ItemStack(material.id, quantity, data.toShort())

fun Material.toBukkit() = org.bukkit.Material.getMaterial(id) ?: throw IllegalArgumentException("Unknown item id $id!")

fun org.bukkit.inventory.ItemStack.toShiroiItem() = Item(type.toShiroi(), data.data)

fun org.bukkit.inventory.ItemStack.toShiroiStack() = ItemStack(type.toShiroi(), data.data, amount)

fun org.bukkit.Material.toShiroi() = Material.getMaterial(id) ?: throw IllegalArgumentException("Unknown item id $id!")