package me.ddevil.shiroi.ui.internal.component.misc.value

class DoubleValueModifierPopulator(val supplier: (Int, Int) -> Double?) : ContainerValueModifier.Populator<Double> {

    override fun get(x: Int, y: Int) = supplier.invoke(x, y)

}