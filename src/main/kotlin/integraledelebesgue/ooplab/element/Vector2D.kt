package integraledelebesgue.ooplab.element

import integraledelebesgue.ooplab.engine.GameProperties
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Vector2D(val x: Int, val y: Int) {

    val length: Double
        get() = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))

    init {
        //require(x >= 0) {}
        //require(y >= 0) {}
        //require(x <= GameProperties.width) {}
        //require(y <= GameProperties.height)
    }

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

    fun distanceTo(other: Vector2D): Double {
        return (this - other).length
    }

    override fun toString(): String {
        return "(x=$x, y=$y)"
    }

    companion object {
        fun randomVectorInBounds(properties: GameProperties): Vector2D {
            return Vector2D(
                abs(Random.nextInt()) % properties.width,
                abs(Random.nextInt()) % properties.height
            )
        }

        fun inBounds(x: Int, y: Int): Boolean {
            return (x in 0 until GameProperties.width) and
                    (y in 0 until GameProperties.height)
        }
    }
}