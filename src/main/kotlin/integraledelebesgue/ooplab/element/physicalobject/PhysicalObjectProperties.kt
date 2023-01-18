package integraledelebesgue.ooplab.element.physicalobject

import javafx.scene.paint.Color

abstract class PhysicalObjectProperties(
    val health: Int,
    val collision: Boolean,
    val color: Color
) {
    init {
        require(health >= 0) {"Physical object's health must be non-negative, got $health"}
    }
}

object WallProperties: PhysicalObjectProperties(200, true, Color.DARKSLATEGRAY)

object MonsterAreaProperties: PhysicalObjectProperties(0, false, Color.ROSYBROWN)

object CastleAreaProperties: PhysicalObjectProperties(0, false, Color.GRAY)

object FinalAreaProperties: PhysicalObjectProperties(0, false, Color.YELLOW)