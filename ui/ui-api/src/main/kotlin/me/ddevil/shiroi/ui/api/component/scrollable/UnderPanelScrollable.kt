package me.ddevil.shiroi.ui.internal.component.scrollable

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSortedSet
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.Component
import me.ddevil.shiroi.ui.api.component.SlotComponent
import me.ddevil.shiroi.ui.api.component.container.Menu
import me.ddevil.shiroi.ui.api.component.scrollable.Scrollable
import me.ddevil.shiroi.ui.api.event.UIActionEvent
import me.ddevil.shiroi.ui.api.event.UIClickEvent
import me.ddevil.shiroi.ui.api.misc.Action
import me.ddevil.shiroi.ui.api.misc.ScrollDirection
import me.ddevil.shiroi.ui.api.updater.ScrollerUpdater
import org.bukkit.inventory.ItemStack
import java.util.*

class UnderPanelScrollable<H : SlotComponent<*>>
@JvmOverloads
constructor(
        expectedType: Class<H>,
        menu: Menu<*>,
        width: Int,
        height: Int,
        updater: ScrollerUpdater<Scrollable<*>>,
        background: ItemStack? = null,
        lowPanelBackground: ItemStack? = null,
        onClickEmptyAction: Action? = null,
        id: String? = null
) : SimpleScrollable<H>(expectedType, width, height - 1, background, id, onClickEmptyAction) {

    private val lowPanel: MutableMap<Int, SlotComponent<*>>
    var lowPanelBackground: ItemStack? = null


    init {
        if (height < 1) {
            throw IllegalArgumentException("A low paned scrollable requires a minimum height of 2! Or maybe you could also use a SingleLaneScrollable :D")
        }
        action = PanelScrollableClickHandler()
        this.lowPanelBackground = lowPanelBackground
        this.lowPanel = HashMap<Int, SlotComponent<*>>()
        lowPanel.put(0, PageScroller(menu, this, ScrollDirection.PREVIOUS, updater))
        lowPanel.put(width - 1, PageScroller(menu, this, ScrollDirection.NEXT, updater))
    }

    private inner class PanelScrollableClickHandler : Action {


        override fun invoke(e: UIClickEvent, localPosition: UIPosition) {
            val clickedSlot = localPosition.toInvSlot(width)
            var obj: Component? = null
            val currentPage = this@UnderPanelScrollable.currentPage
            if (localPosition.y == maxY) {
                //Was on panel
                val x = localPosition.x
                if (lowPanel.containsKey(x)) {
                    obj = lowPanel[x]
                }
            } else if (currentPage.containsKey(clickedSlot)) {
                obj = currentPage[clickedSlot]
            }
            val objLocalPos = e.clickedSlot - localPosition
            if (obj != null) {
                obj.update()
                if (obj is Clickable) {
                    val c = obj
                    c.action.invoke(e, objLocalPos)
                    UIActionEvent(c, e.clickedSlot, e.player, e.clickType, e.placedBlock).call()
                }
            } else if (onClickEmptyAction != null) {
                onClickEmptyAction!!.invoke(e, objLocalPos)
            }
        }
    }

    override val size: Int
        get() = (height + 1) * width

    override val area: SortedSet<UIPosition>
        get() {
            val menuPositionBuilder = ImmutableSortedSet.Builder(Comparator<UIPosition>(UIPosition::compareTo))
            for (x in 0 .. maxX) {
                for (y in 0 .. maxY) {
                    menuPositionBuilder.add(UIPosition(x, y))
                }
            }
            return menuPositionBuilder.build()
        }

    override fun draw(): Map<UIPosition, ItemStack> {
        val builder = ImmutableMap.Builder<UIPosition, ItemStack>().putAll(draw(0, width - 1, 0, height - 2))
        for (x in 0 .. width - 1) {
            val item = lowPanel[x]
            val pos = UIPosition(x, maxY)
            if (item != null) {
                item.update()
                builder.put(pos, item.icon)
            } else if (hasLowPanelBackground()) {
                builder.put(pos, lowPanelBackground!!)
            }
        }
        return builder.build()
    }

    fun hasObjectInLowPanel(slot: Int): Boolean {
        return lowPanel.containsKey(slot)
    }

    fun placeInLowPanel(slotComponent: SlotComponent<*>, slot: Int) {
        if (hasObjectInLowPanel(slot)) {
            throw IllegalArgumentException("There is already an object in low panel slot $slot!")
        }
        lowPanel.put(slot, slotComponent)
    }

    fun removeFromLowPanel(slot: Int) {
        if (hasObjectInLowPanel(slot)) {
            lowPanel.remove(slot)
        }
    }

    fun hasLowPanelBackground(): Boolean {
        return lowPanelBackground != null
    }

    override var height: Int
        get() {
            return super.height + 1
        }
        set(value) {
            super.height = value
        }
}

