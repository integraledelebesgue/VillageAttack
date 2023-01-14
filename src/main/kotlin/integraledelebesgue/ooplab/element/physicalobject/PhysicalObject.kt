package integraledelebesgue.ooplab.element.physicalobject

import integraledelebesgue.ooplab.element.Direction
import integraledelebesgue.ooplab.element.Vector2D
import java.util.BitSet


sealed class PhysicalObject(position: Vector2D, properties: PhysicalObjectProperties) {

    class Wall(position: Vector2D, properties: PhysicalObjectProperties): PhysicalObject(position, properties) {

        var health: Int = 0

        private lateinit var shapeMask: BitSet

        init {
            var health = properties.health
        }

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
}