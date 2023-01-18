package integraledelebesgue.ooplab.map.generator.castle

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


sealed class CastleProvider {

    protected val width: Int = GameProperties.width
    protected val height: Int = GameProperties.height

    protected val a: Int = (0.2 * width).toInt()
    private val b: Int = (0.2 * height).toInt()

    protected val centre: Vector2D = Vector2D(width / 2, height / 2)

    private val pointsCount: Int = (PI * (1.5 * (a + b) - sqrt((a * b).toDouble()))).times(2.0).toInt()

    protected val domain: D1Array<Double> = mk.linspace(0.0, 2.1 * PI, pointsCount)

    abstract suspend fun generate()

    init {
        println(pointsCount)
    }

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

}


object SimpleCastleProvider : CastleProvider() {

    override suspend fun generate() {
        buildWalls(
            generatePoints(0.0).plus(centre.x.toDouble()),
            generatePoints(PI/2).plus(centre.y.toDouble())
        )
    }

    private fun generatePoints(phase: Double): D1Array<Double> {
        return domain
            .plus(phase)
            .cos()
            .times(0.25 * max(width, height))
    }

}


object FancyCastleProvider : CastleProvider() {

    override suspend fun generate() {
        buildWalls(
            generatePoints().plus(centre.x.toDouble().div(1.5)),
            generatePoints().plus(centre.y.toDouble().div(1.5))
        )
    }

    private fun generatePoints(): D1Array<Double> {
        val coordinates: NDArray<Double, D1> = mk.zeros(domain.size)

        // Inverse-Fourier-transform-like closed curve generation
        for (i in 1..5) {
            coordinates.plusAssign(
                domain
                    .copy()
                    .plus(java.util.Random().nextGaussian())
                    .apply { if (Random.nextBoolean()) this.cos() else this.sin() }
                    .apply { map {it.pow(i)} }
                    .times(Random.nextDouble())
            )
        }

        return coordinates
    }
}

