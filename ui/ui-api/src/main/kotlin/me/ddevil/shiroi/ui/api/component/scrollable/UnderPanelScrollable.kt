package me.ddevil.shiroi.ui.api.component.scrollable

import com.google.common.collect.ImmutableMap
import me.ddevil.shiroi.ui.api.UIPosition
import me.ddevil.shiroi.ui.api.component.Clickable
import me.ddevil.shiroi.ui.api.component.Component
import me.ddevil.shiroi.ui.api.component.SlotComponent
import me.ddevil.shiroi.ui.api.component.container.Menu
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
        id: String? = null
) : AbstractScrollable<H>(expectedType, width, height, width, height - 1, background, id) {


    private val lowPanel: MutableMap<Int, SlotComponent<*>>
    var lowPanelBackground: ItemStack? = null
    override val action: Action

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

    init {
        if (height < 1) {
            throw IllegalArgumentException("A low paned scrollable requires a minimum height of 2! Or maybe you could also use a SingleLaneScrollable :D")
        }
        this.action = PanelScrollableClickHandler()
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
            var pageObj: H? = null
            if (localPosition.y == maxY) {
                //Was on panel
                val x = localPosition.x
                if (lowPanel.containsKey(x)) {
                    obj = lowPanel[x]
                }
            } else if (currentPage.containsKey(clickedSlot)) {
                pageObj = currentPage[clickedSlot]
                obj = pageObj
            }
            val objLocalPos = e.clickedSlot - localPosition
            if (obj != null) {
                obj.update()
                if (obj is Clickable) {
                    val c = obj
                    c.action.invoke(e, objLocalPos)
                    UIActionEvent(c, e.clickedSlot, e.player, e.clickType, e.placedBlock).call()
                }
            }
            for (listener in listeners) {
                listener.onClick(pageObj, e)
            }

        }
    }

    override fun draw(fromX: Int, toX: Int, fromY: Int, toY: Int): Map<UIPosition, ItemStack> {
        val builder = ImmutableMap.Builder<UIPosition, ItemStack>()
        val addedObjects = HashSet<Component>()
        if (debug) {
            println("Drawing component '$this'")
        }
        val xRange = fromX..toX
        val yRange = fromY..toY
        for (y in 0..pageHeight - 1) {
            if (y !in yRange) {
                break
            }
            for (x in 0..pageWidth - 1) {
                if (x !in xRange) {
                    break
                }
                val currentPos = UIPosition(x, y)
                val uiObject = currentPage[currentPos.toInvSlot(width)]
                if (uiObject != null) {
                    if (uiObject !in addedObjects) {
                        addedObjects.add(uiObject)
                        val childUI = uiObject.draw()
                        for ((childPos, item) in childUI) {
                            builder.put(childPos + currentPos, item)
                        }

                        if (debug) {
                            println("Drawing child component $uiObject  @ $currentPos")
                        }
                    } else {
                        println("Found object $uiObject  @ $currentPos, but was already drawn, skipping.")
                    }
                } else if (hasBackground()) {
                    builder.put(currentPos, background)

                    if (debug) {
                        println("No component @ $currentPos, using background.")
                    }
                } else if (debug) {
                    println("No component @ $currentPos and no background.")
                }
            }
        }
        if (maxY in yRange) {
            for (x in 0..pageWidth - 1) {
                if (x !in xRange) {
                    break
                }
                val panelObj = lowPanel[x]
                if (panelObj != null) {
                    val currentPos = UIPosition(x, maxY)
                    val childUI = panelObj.draw()
                    for ((childPos, item) in childUI) {
                        builder.put(childPos + currentPos, item)
                    }
                }
            }
        }
        return builder.build()
    }


}

