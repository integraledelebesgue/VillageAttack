package integraledelebesgue.ooplab.element.creature

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import java.util.*
import kotlin.reflect.KClass

sealed class Creature(
    initialPosition: Vector2D,
    creatureProperties: CreatureProperties
) {
    private val uuid: UUID = UUID.randomUUID()

    var position: Vector2D = initialPosition

    var health = creatureProperties.health
    var isAlive = true
    val color = creatureProperties.color
    val damage = creatureProperties.damage
    val range = creatureProperties.range
    val attackSpeed = creatureProperties.attackSpeed
    val movesPerTurn = creatureProperties.movesPerTurn

    val team = creatureProperties.team

    val moveProvider = creatureProperties.moveProvider

    private var attack = 0
    val hasAttack: Boolean
        get() = attack == 0

    fun increaseAttack() {
        attack += 1
        attack %= attackSpeed
    }

    fun takeDamage(damage: Int) {
        health -= damage
        if(health <= 0) isAlive = false
    }

    private fun testCollisionWithWall(position: Vector2D): Boolean {
        return WallFactory.storage[position] != null
    }

    companion object {
        val attackers: List<KClass<out Creature>> = listOf(Werewolf::class, Mummy::class, Zombie::class)
        val defenders: List<KClass<out Creature>> = listOf(Archer::class, Crossbower::class, Mage::class)

        val properties: Map<KClass<out Creature>, CreatureProperties> = hashMapOf(
            Werewolf::class to WarewolfProperties,
            Mummy::class to MummyProperties,
            Zombie::class to ZombieProperties,
            Archer::class to ArcherProperties,
            Crossbower::class to CrossbowerProperties,
            Mage::class to MageProperties
        )

        val factory: Map<KClass<out Creature>, CreatureFactory> = hashMapOf(
            Werewolf::class to WerewolfFactory,
            Mummy::class to MummyFactory,
            Zombie::class to ZombieFactory,
            Archer::class to ArcherFactory,
            Crossbower::class to CrossbowerFactory,
            Mage::class to MageFactory
        )
    }

    class Werewolf(initialPosition: Vector2D, creatureProperties: CreatureProperties): Creature(initialPosition, creatureProperties)

    class Mummy(initialPosition: Vector2D, creatureProperties: CreatureProperties): Creature(initialPosition, creatureProperties)

    class Zombie(initialPosition: Vector2D, creatureProperties: CreatureProperties): Creature(initialPosition, creatureProperties)

    class Archer(initialPosition: Vector2D, creatureProperties: CreatureProperties): Creature(initialPosition, creatureProperties)

    class Crossbower(initialPosition: Vector2D, creatureProperties: CreatureProperties): Creature(initialPosition, creatureProperties)

    class Mage(initialPosition: Vector2D, creatureProperties: CreatureProperties): Creature(initialPosition, creatureProperties)

    override fun toString(): String {
        return "${this::class.simpleName} #$uuid"
    }

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