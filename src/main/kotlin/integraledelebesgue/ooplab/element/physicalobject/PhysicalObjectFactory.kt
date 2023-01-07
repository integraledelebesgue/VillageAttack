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
        val newWall: PhysicalObject.Wall = PhysicalObject.Wall(position, WallProperties)
        storage[position] = newWall
        globalStorage[position] = newWall
    }
}

object MonsterTerritoryFactory: PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        val newTerritory: PhysicalObject.MonsterTerritory = PhysicalObject.MonsterTerritory(position, MonsterTerritoryProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}

object FinalTerritoryFactory: PhysicalObjectFactory {
    override val storage: MutableMap<Vector2D, PhysicalObject> = HashMap()

    override fun create(position: Vector2D) {
        val newTerritory: PhysicalObject.FinalTerritory = PhysicalObject.FinalTerritory(position, FinalTerritoryProperties)
        storage[position] = newTerritory
        globalStorage[position] = newTerritory
    }
}
