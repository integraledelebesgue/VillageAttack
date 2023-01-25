package integraledelebesgue.ooplab.element

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

data class Vector2D(val x: Int, val y: Int) {

    operator fun plus(other: Vector2D): Vector2D {
        return Vector2D(
            x + other.x,
            y + other.y
        )
    }

    operator fun minus(other: Vector2D): Vector2D {
        return Vector2D(
            x - other.x,
            y - other.y
        )
    }

    operator fun times(scalar: Int): Vector2D {
        return Vector2D(
            x * scalar,
            y * scalar
        )
    }

    fun distanceTo(other: Vector2D): Int {
        return max(
            abs(x - other.x),
            abs(y - other.y)
        )
    }

    fun euclideanDistanceTo(other: Vector2D): Double {
        return sqrt( (x - other.x).toDouble().pow(2) + (y - other.y).toDouble().pow(2) )
    }

    override fun toString(): String {
        return "(x=$x, y=$y)"
    }

}