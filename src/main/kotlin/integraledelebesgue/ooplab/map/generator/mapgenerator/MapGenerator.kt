package integraledelebesgue.ooplab.map.generator.mapgenerator

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import integraledelebesgue.ooplab.engine.GameProperties
import org.jetbrains.kotlinx.multik.api.linspace
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.default.math.DefaultMathEx.cos
import org.jetbrains.kotlinx.multik.default.math.DefaultMathEx.sin
import org.jetbrains.kotlinx.multik.ndarray.data.D1
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.*
import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import kotlin.math.*
import kotlin.random.Random


class MapGenerator(gameProperties: GameProperties) {

    private val width: Int = gameProperties.width
    private val height: Int = gameProperties.height

    private val a: Int = (0.3 * width).toInt()
    private val b: Int = (0.3 * height).toInt()

    private val centre: Vector2D = Vector2D(width / 2, height / 2)

    fun generateSimpleWall() {
        val domain: D1Array<Double> = mk.linspace<Double>(0.0, 2*PI, (PI * (1.5 * (a + b) - sqrt((a*b).toDouble()))).toInt())
        val xCoordinates: DoubleArray = cos(domain).times(a.toDouble()).plus(centre.x.toDouble()).toDoubleArray()
        val yCoordinates: DoubleArray = sin(domain).times(b.toDouble()).plus(centre.y.toDouble()).toDoubleArray()

        xCoordinates.zip(yCoordinates).forEach {
            apply {
                WallFactory.create(Vector2D(it.first.toInt(), it.second.toInt()))
            }
        }
    }

    fun generateFancydWall() {
        // Preparation
        val domain: D1Array<Double> = mk.linspace<Double>(0.0, 2*PI, (PI * (1.5 * (a + b) - sqrt((a*b).toDouble()))).toInt())
        val xCoordinates: NDArray<Double, D1> = mk.zeros(domain.size)
        val yCoordinates: NDArray<Double, D1> = mk.zeros(domain.size)

        // Inverse-Fourier-transform-like closed curve generation
        for(i in 1..5) {
            xCoordinates.plusAssign(
                if(Random.nextBoolean()) cos(domain.plus(java.util.Random().nextGaussian())).map{it.pow(i)}.times(2 * Random.nextDouble() - 1.0)
                else sin(domain.plus(java.util.Random().nextGaussian())).map{it.pow(i)}.times(2 * Random.nextDouble() - 1.0)
            )

            yCoordinates.plusAssign(
                if(Random.nextBoolean()) cos(domain.plus(java.util.Random().nextGaussian())).times(2 * Random.nextDouble() - 1.0)
                else sin(domain.plus(java.util.Random().nextGaussian())).times(2 * Random.nextDouble() - 1.0)
            )
        }

        // Scaling to map fit ~60% of map width/height
        val sizeFactor: Double = max(
            xCoordinates.map { abs(it) }.max()!!,
            yCoordinates.map { abs(it) }.max()!!
        ).div(0.3 * max(width, height))

        xCoordinates.timesAssign(sizeFactor)
        yCoordinates.timesAssign(sizeFactor)

        // Discretization, wall generation
        xCoordinates.toDoubleArray().zip(yCoordinates.toDoubleArray()).forEach {
            apply {
                WallFactory.create(Vector2D(it.first.toInt(), it.second.toInt()))
            }
        }
    }

}