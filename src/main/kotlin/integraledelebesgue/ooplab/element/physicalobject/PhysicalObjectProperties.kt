package integraledelebesgue.ooplab.element.physicalobject

abstract class PhysicalObjectProperties(
    val health: Int,
    val collision: Boolean
) {
    init {
        require(health >= 0) {"Physical object's health must be non-negative, got $health"}
    }
}

object WallProperties: PhysicalObjectProperties(200, true)

object MonsterAreaProperties: PhysicalObjectProperties(0, false)

object CastleAreaProperties: PhysicalObjectProperties(0, false)

object FinalAreaProperties: PhysicalObjectProperties(0, false)