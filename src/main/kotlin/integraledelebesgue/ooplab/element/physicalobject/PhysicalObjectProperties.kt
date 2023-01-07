package integraledelebesgue.ooplab.element.physicalobject

abstract class PhysicalObjectProperties(
    val health: Int,
    val collision: Boolean
)

object WallProperties: PhysicalObjectProperties(200, true)

object MonsterTerritoryProperties: PhysicalObjectProperties(0, false)

object FinalTerritoryProperties: PhysicalObjectProperties(0, false)