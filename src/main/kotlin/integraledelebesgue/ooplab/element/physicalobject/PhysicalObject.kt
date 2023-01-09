package integraledelebesgue.ooplab.element.physicalobject

import integraledelebesgue.ooplab.element.Vector2D

sealed class PhysicalObject(position: Vector2D, properties: PhysicalObjectProperties) {

    class Wall(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties) {
        init {
            lateinit var shape: WallShape
        }
    }

    class MonsterArea(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties)

    class CastleArea(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties)

    class FinalArea(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties)
}