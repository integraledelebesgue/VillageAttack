package integraledelebesgue.ooplab.element.creature

import integraledelebesgue.ooplab.element.Vector2D
import java.util.UUID

sealed class Creature(var position: Vector2D, private val creatureProperties: CreatureProperties) {

    private val uuid: UUID = UUID.randomUUID()

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Creature

        if (uuid != other.uuid) return false

        return true
    }

    init {
        var health = creatureProperties.health
    }

    class Warewolf(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Mummy(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Zombie(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Archer(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Crossbower(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Mage(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

}