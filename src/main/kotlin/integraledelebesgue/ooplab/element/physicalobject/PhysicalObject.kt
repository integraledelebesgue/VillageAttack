package integraledelebesgue.ooplab.element.physicalobject

import integraledelebesgue.ooplab.element.Direction
import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.element.creature.CreatureProperties
import java.util.BitSet
import kotlin.reflect.KClass


sealed class PhysicalObject(private val position: Vector2D, properties: PhysicalObjectProperties) {

    var health: Int = properties.health
    var isAlive: Boolean = true

    val color = properties.color

    fun takeDamage(damage: Int) {
        health -= damage
        if(health <= 0) isAlive = false
    }

    override fun toString(): String {
        return "${this::class.simpleName} ${this.position}, HP: $health"
    }

    class Wall(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties) {

        private lateinit var shapeMask: BitSet

        fun getShapeID(): Int {
            return shapeMask.toByteArray()[0].toInt()
        }

        fun setShape() {
            shapeMask = shapeMask()
        }

        private fun shapeMask(): BitSet {
            val neighbourSet = BitSet(8)

            for(vectorIndex in (0..7).zip(Direction.values().map{it.unitVector}))
                neighbourSet[vectorIndex.first] = WallFactory.storage[vectorIndex.second] != null

            return neighbourSet
        }
    }


    class MonsterArea(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties)

    class CastleArea(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties)

    class FinalArea(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties)

    companion object {
        val properties: Map<KClass<out PhysicalObject>, PhysicalObjectProperties> = hashMapOf(
            Wall::class to WallProperties,
            MonsterArea::class to MonsterAreaProperties,
            CastleArea::class to CastleAreaProperties,
            FinalArea::class to FinalAreaProperties
        )

        val factory: Map<KClass<out PhysicalObject>, PhysicalObjectFactory> = hashMapOf(
            Wall::class to WallFactory,
            //MonsterArea::class to MonsterAreaFactory,
            //CastleArea::class to CastleAreaFactory,
            //FinalArea::class to FinalAreaFactory
        )
    }
}