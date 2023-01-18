package integraledelebesgue.ooplab.element.creature

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import org.knowm.xchart.style.markers.Cross
import java.util.UUID
import kotlin.reflect.KClass

sealed class Creature(var position: Vector2D, val creatureProperties: CreatureProperties) {

    var health = creatureProperties.health
    var isAlive = true
    val color = creatureProperties.color

    private val uuid: UUID = UUID.randomUUID()

    fun takeDamage(damage: Int) {
        health -= damage
        if(health <= 0) isAlive = false
    }

    fun testCollisionWithWall(): Boolean {
        return WallFactory.storage[position] != null
    }

    companion object {
        val attackers: List<KClass<out Creature>> = listOf(Warewolf::class, Mummy::class, Zombie::class)
        val defenders: List<KClass<out Creature>> = listOf(Archer::class, Crossbower::class, Mage::class)

        val properties: Map<KClass<out Creature>, CreatureProperties> = hashMapOf(
            Warewolf::class to WarewolfProperties,
            Mummy::class to MummyProperties,
            Zombie::class to ZombieProperties,
            Archer::class to ArcherProperties,
            Crossbower::class to CrossbowerProperties,
            Mage::class to MageProperties
        )

        val factory: Map<KClass<out Creature>, CreatureFactory> = hashMapOf(
            Warewolf::class to WarewolfFactory,
            Mummy::class to MummyFactory,
            Zombie::class to ZombieFactory,
            Archer::class to ArcherFactory,
            Crossbower::class to CrossbowerFactory,
            Mage::class to MageFactory
        )
    }

    class Warewolf(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Mummy(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Zombie(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Archer(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Crossbower(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)

    class Mage(position: Vector2D, creatureProperties: CreatureProperties): Creature(position, creatureProperties)


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
}