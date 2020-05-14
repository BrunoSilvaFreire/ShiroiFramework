package me.ddevil.shiroi.schematic

import me.ddevil.util.math.vector.IntVector3
import me.ddevil.util.math.vector.Vector3

enum class SchematicRotation {
    FORWARD {
        override fun getPosition(ox: Int, oy: Int, oz: Int, x: Int, y: Int, z: Int): Vector3<Int> {
            return IntVector3(ox + x, oy + y, oz + z)
        }
    },
    LEFT {
        override fun getPosition(ox: Int, oy: Int, oz: Int, x: Int, y: Int, z: Int): Vector3<Int> {
            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    RIGHT {
        override fun getPosition(ox: Int, oy: Int, oz: Int, x: Int, y: Int, z: Int): Vector3<Int> {
            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    BACKWARDS {
        override fun getPosition(ox: Int, oy: Int, oz: Int, x: Int, y: Int, z: Int): Vector3<Int> {
            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    UPWARDS {
        override fun getPosition(ox: Int, oy: Int, oz: Int, x: Int, y: Int, z: Int): Vector3<Int> {
            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    },
    DOWNWARDS {
        override fun getPosition(ox: Int, oy: Int, oz: Int, x: Int, y: Int, z: Int): Vector3<Int> {
            return IntVector3(ox - x, oy + y, oz + z)
        }
    };

    abstract fun getPosition(ox: Int, oy: Int, oz: Int, x: Int, y: Int, z: Int): Vector3<Int>
}