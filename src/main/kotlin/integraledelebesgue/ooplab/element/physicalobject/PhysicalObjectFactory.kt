package integraledelebesgue.ooplab.element.physicalobject

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObjectFactory.Companion.globalStorage

sealed interface PhysicalObjectFactory {

    val storage: MutableMap<Vector2D, PhysicalObject>

    fun create(position: Vector2D)

    fun isOccupied(position: Vector2D): Boolean {
        return storage[position] != null
    }

    companion object {
        val globalStorage: MutableMap<Vector2D, PhysicalObject> = HashMap()

        suspend fun removeBrokenPhysicalObjects() {
            PhysicalObject.factory.values.forEach { objectFactory ->
                objectFactory.storage.keys
                    .removeIf {
                        !(objectFactory.storage[it]?.isAlive ?: false)
                    }
            }
        }
    }
}

object WallFactory : PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = LinkedHashMap()

    override fun create(position: Vector2D) {
        if (storage.getOrDefault(position, null) != null)
            return

        val newWall: PhysicalObject.Wall = PhysicalObject.Wall(position, WallProperties)
        storage[position] = newWall
        globalStorage[position] = newWall
    }

    suspend fun setShapes() {
        storage.values.forEach { (it as PhysicalObject.Wall).setShape() }
    }
}

object MonsterAreaFactory : PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if (WallFactory.storage.getOrDefault(position, null) != null)
            return

        val newTerritory: PhysicalObject.MonsterArea = PhysicalObject.MonsterArea(position, MonsterAreaProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}

object CastleAreaFactory : PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if (WallFactory.storage.getOrDefault(position, null) != null)
            return

        val newTerritory: PhysicalObject.CastleArea = PhysicalObject.CastleArea(position, CastleAreaProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}

object FinalAreaFactory : PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if (WallFactory.storage.getOrDefault(position, null) != null)
            return

        val newTerritory: PhysicalObject.FinalArea = PhysicalObject.FinalArea(position, FinalAreaProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}
