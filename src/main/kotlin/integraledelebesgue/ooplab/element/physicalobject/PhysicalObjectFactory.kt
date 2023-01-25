package integraledelebesgue.ooplab.element.physicalobject

import integraledelebesgue.ooplab.element.Vector2D

sealed class PhysicalObjectFactory {

    abstract val storage: MutableMap<Vector2D, PhysicalObject>

    abstract fun create(position: Vector2D)

    @Synchronized
    fun isOccupied(position: Vector2D): Boolean {
        return storage[position]?.isAlive ?: false
    }

    companion object {
        val globalStorage: MutableMap<Vector2D, PhysicalObject> = HashMap()
    }
}

object WallFactory : PhysicalObjectFactory() {
    override val storage: MutableMap<Vector2D, PhysicalObject> = LinkedHashMap()

    override fun create(position: Vector2D) {
        if (storage.getOrDefault(position, null) != null)
            return

        val newWall: PhysicalObject.Wall = PhysicalObject.Wall(position, WallProperties)
        storage[position] = newWall
        globalStorage[position] = newWall
    }

}

object MonsterAreaFactory : PhysicalObjectFactory() {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if (WallFactory.storage.getOrDefault(position, null) != null)
            return

        val newTerritory: PhysicalObject.MonsterArea = PhysicalObject.MonsterArea(position, MonsterAreaProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}
