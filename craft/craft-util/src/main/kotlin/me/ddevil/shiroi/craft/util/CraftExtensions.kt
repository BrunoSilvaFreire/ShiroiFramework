package me.ddevil.shiroi.craft.util


import me.ddevil.shiroi.util.misc.item.Item
import me.ddevil.shiroi.util.misc.item.ItemStack
import me.ddevil.shiroi.util.misc.item.Material
import me.ddevil.util.vector.DoubleVector3
import me.ddevil.util.vector.Vector2
import me.ddevil.util.vector.Vector3
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.block.Action
import org.bukkit.util.Vector


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

@JvmOverloads
fun Vector2<*>.toLocation(world: World, z: Number = 0) = Location(world, x.toDouble(), y.toDouble(), z.toDouble())

fun Vector3<*>.toLocation(world: World) = Location(world, x.toDouble(), y.toDouble(), z.toDouble())

@JvmOverloads
fun Vector2<*>.toVector(z: Number = 0) = Vector(x.toDouble(), y.toDouble(), z.toDouble())

fun Vector3<*>.toVector() = Vector(x.toDouble(), y.toDouble(), z.toDouble())

fun Location.toVector3(): Vector3<Double> = DoubleVector3(x, y, z)

fun Vector.toVector3(): Vector3<Double> = DoubleVector3(x, y, z)
