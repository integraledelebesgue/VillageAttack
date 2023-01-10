package integraledelebesgue.ooplab.map.generator.mapgenerator

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import integraledelebesgue.ooplab.engine.GameProperties
import org.jetbrains.kotlinx.multik.api.linspace
import org.jetbrains.kotlinx.multik.api.math.cos
import org.jetbrains.kotlinx.multik.api.math.sin
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.D1
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.*
import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import kotlin.math.*
import kotlin.random.Random


sealed class MapGenerator(gameProperties: GameProperties) {

    protected val width: Int = gameProperties.width
    protected val height: Int = gameProperties.height

    private val a: Int = (0.3 * width).toInt()
    private val b: Int = (0.3 * height).toInt()

    protected val centre: Vector2D = Vector2D(width / 2, height / 2)

    private val pointsCount: Int = (PI * (1.5 * (a + b) - sqrt((a * b).toDouble()))).toInt()

    protected val domain: D1Array<Double> = mk.linspace(0.0, 2 * PI, pointsCount)

    abstract fun generate()

    protected fun buildWalls(xCoordinates: D1Array<Double>, yCoordinates: D1Array<Double>) {
        xCoordinates
            .toDoubleArray()
            .zip(yCoordinates.toDoubleArray())
            .forEach {
                apply {
                    WallFactory.create(Vector2D(it.first.toInt(), it.second.toInt()))
                }
            }
    }


    class SimpleGenerator(gameProperties: GameProperties) : MapGenerator(gameProperties) {

        override fun generate() {
            buildWalls(
                generatePoints(0.0),
                generatePoints(PI/2)
            )
        }

        private fun generatePoints(phase: Double): D1Array<Double> {
            return domain
                .plus(phase)
                .cos()
                .times(0.3 * max(width, height))
        }

    }


    class FancyGenerator(gameProperties: GameProperties) : MapGenerator(gameProperties) {

        override fun generate() {
            buildWalls(
                generatePoints().plus(centre.x.toDouble()),
                generatePoints().plus(centre.y.toDouble())
            )
        }

        private fun generatePoints(): D1Array<Double> {
            val coordinates: NDArray<Double, D1> = mk.zeros(domain.size)

            // Inverse-Fourier-transform-like closed curve generation
            for (i in 1..5) {
                coordinates.plusAssign(
                    domain
                        .plus(java.util.Random().nextGaussian())
                        .apply { if (Random.nextBoolean()) this.cos() else this.sin() }
                        .map { it.pow(i) }
                        .times(2 * Random.nextDouble() - 1.0)
                )
            }

            // Scaling to map size
            coordinates.timesAssign(coordinates.max()!!.div(3.0 * max(width, height)))

            return coordinates

        }

    }

}