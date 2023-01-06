package integraledelebesgue.ooplab.element

import integraledelebesgue.ooplab.engine.Properties
import kotlin.math.abs
import kotlin.random.Random

data class Vector2D(val x: Int, val y: Int) {

    operator fun plus(other: Vector2D): Vector2D {
        return Vector2D(
            x + other.x,
            y + other.y
        )
    }

    companion object {
        fun randomVectorInBounds(properties: Properties): Vector2D {
            return Vector2D(
                abs(Random.nextInt()) % properties.width,
                abs(Random.nextInt()) % properties.height
            )
        }
    }
}