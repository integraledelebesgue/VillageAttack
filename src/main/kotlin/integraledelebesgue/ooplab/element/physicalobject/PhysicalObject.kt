package integraledelebesgue.ooplab.element.physicalobject

import integraledelebesgue.ooplab.element.Vector2D
import kotlin.reflect.KClass

sealed class PhysicalObject(private val position: Vector2D, properties: PhysicalObjectProperties) {

    private var health: Int = properties.health
    var isAlive: Boolean = true

    val color = properties.color

    fun takeDamage(damage: Int) {
        health -= damage
        if(health <= 0) {
            isAlive = false
            PhysicalObjectFactory.globalStorage.remove(position)
        }
    }

    override fun toString(): String {
        return "${this::class.simpleName} ${this.position}, HP: $health"
    }

    class Wall(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties)

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
            MonsterArea::class to MonsterAreaFactory
        )
    }
}