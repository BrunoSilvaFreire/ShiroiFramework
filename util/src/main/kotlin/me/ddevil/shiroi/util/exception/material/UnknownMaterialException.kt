package me.ddevil.shiroi.util.exception.material

class UnknownMaterialException(materialName: String) : IllegalArgumentException("Unknown material $materialName")