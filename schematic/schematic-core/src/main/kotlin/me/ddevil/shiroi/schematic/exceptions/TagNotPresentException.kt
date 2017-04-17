package me.ddevil.shiroi.schematic.exceptions

class TagNotPresentException(tagName: String) : IllegalStateException("Couldn't find tag '$tagName' in schematic!")