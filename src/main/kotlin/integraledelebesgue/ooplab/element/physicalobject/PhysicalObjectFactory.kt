package integraledelebesgue.ooplab.element.physicalobject

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObjectFactory.Companion.globalStorage

sealed interface PhysicalObjectFactory {

    val storage: MutableMap<Vector2D, PhysicalObject>

    fun create(position: Vector2D)

    companion object {
        val globalStorage: MutableMap<Vector2D, PhysicalObject> = HashMap()
    }
}

object WallFactory: PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if(storage.getOrDefault(position, null) != null)
            return

        val newWall: PhysicalObject.Wall = PhysicalObject.Wall(position, WallProperties)
        storage[position] = newWall
        globalStorage[position] = newWall
    }
}

object MonsterTerritoryFactory: PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if(WallFactory.storage.getOrDefault(position, null) != null)
            return

        val newTerritory: PhysicalObject.MonsterArea = PhysicalObject.MonsterArea(position, MonsterAreaProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}

object CastleTerritoryFactory: PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if(WallFactory.storage.getOrDefault(position, null) != null)
            return

        val newTerritory: PhysicalObject.CastleArea = PhysicalObject.CastleArea(position, CastleAreaProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}

object FinalTerritoryFactory: PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        if(WallFactory.storage.getOrDefault(position, null) != null)
            return

        val newTerritory: PhysicalObject.FinalArea = PhysicalObject.FinalArea(position, FinalAreaProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}
