package me.ddevil.shiroi.schematic.exceptions

import org.jnbt.Tag

class IllegalTagException(tag: Tag,
                          expectedTypeNotPresentException: Class<out Tag>) : IllegalStateException("Expected type '${expectedTypeNotPresentException.simpleName}' for tag '${tag.name}' but found '${tag.javaClass.simpleName}'!")