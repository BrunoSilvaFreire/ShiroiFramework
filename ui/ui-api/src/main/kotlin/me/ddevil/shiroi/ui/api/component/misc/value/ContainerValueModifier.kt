package me.ddevil.shiroi.ui.api.component.misc.value

import me.ddevil.shiroi.ui.api.component.container.ArrayContainer
import me.ddevil.shiroi.ui.api.updater.ItemUpdater
import org.bukkit.inventory.ItemStack


class ContainerValueModifier<T> : ArrayContainer<ValueModifierItem<*>>, ValueModifier<T> {

    interface Populator<out T> {
        fun get(x: Int, y: Int): T?
    }

    data class Options<T> constructor(
            val onModifiedListener: OnValueModifiedListener<T>,
            val valuePopulator: Populator<T>,
            val initialItemCreator: (T, Int, Int) -> ItemStack,
            val itemUpdater: (T, Int, Int, ItemStack) -> ItemStack
    )

    data class SimpleOptions<T> constructor(
            val onModifiedListener: OnValueModifiedListener<T>,
            val valuePopulator: Populator<T>,
            val initialItemCreator: (T, Int, Int) -> ItemStack
    )

    class ModifierItemUpdater<T> constructor(
            val x: Int,
            val y: Int,
            val value: T,
            val itemUpdater: (T, Int, Int, ItemStack) -> ItemStack
    ) : ItemUpdater {

        override fun update(oldItem: ItemStack): ItemStack {
            return itemUpdater.invoke(value, x, y, oldItem)
        }

    }

    override var onModifiedListener: OnValueModifiedListener<T>

    @JvmOverloads
    constructor(width: Int,
                height: Int,
                options: Options<T>,
                background: ItemStack? = null,
                id: String? = null
    ) : super(ValueModifierItem::class.java, width, height, background, id) {

        this.onModifiedListener = options.onModifiedListener

        outer@ for (x in 0 .. width - 1) {
            for (y in 0 .. height - 1) {
                //Create modifier item
                val defaultValue = options.valuePopulator.get(x, y) ?: break@outer
                val updater = ModifierItemUpdater(x, y, defaultValue, options.itemUpdater)

                val item = ValueModifierItem(updater,
                        options.initialItemCreator.invoke(defaultValue, x, y),
                        null,
                        defaultValue,
                        this
                )
                place(item, x, y)
            }
        }

    }

    @JvmOverloads
    constructor(width: Int,
                height: Int,
                simpleOptions: SimpleOptions<T>,
                background: ItemStack? = null,
                id: String? = null
    ) : super(ValueModifierItem::class.java, width, height, background, id) {

        this.onModifiedListener = simpleOptions.onModifiedListener

        outer@ for (x in 0 .. width - 1) {
            for (y in 0 .. height - 1) {
                val value = simpleOptions.valuePopulator.get(x, y) ?: break@outer
                val item = ValueModifierItem(
                        simpleOptions.initialItemCreator.invoke(value, x, y),
                        null,
                        value,
                        this
                )
                place(item, x, y)
            }
        }

    }
}


