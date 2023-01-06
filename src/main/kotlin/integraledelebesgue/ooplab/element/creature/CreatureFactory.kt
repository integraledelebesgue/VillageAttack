package integraledelebesgue.ooplab.element.creature

import integraledelebesgue.ooplab.element.Vector2D


sealed interface CreatureFactory {

    val storage: MutableSet<Creature>

    fun create(position: Vector2D)

    companion object {
        val globalStorage: MutableSet<Creature> = HashSet()
    }
}

object WarewolfFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Warewolf(position, CreatureProperties.Companion.WarewolfProperties())
        storage.add(newCreature)
        CreatureFactory.globalStorage.add(newCreature)
    }
}

object MummyFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Mummy(position, CreatureProperties.Companion.MummyProperties())
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.globalStorage.add(newCreature)
    }
}

object ZombieFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Zombie(position, CreatureProperties.Companion.ZombieProperties())
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.globalStorage.add(newCreature)
    }
}

object ArcherFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Archer(position, CreatureProperties.Companion.ArcherProperties())
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.globalStorage.add(newCreature)
    }
}

object CrossbowerFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Crossbower(position, CreatureProperties.Companion.CrossbowerProperties())
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.globalStorage.add(newCreature)
    }
}

object MageFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Mage(position, CreatureProperties.Companion.MageProperties())
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.globalStorage.add(newCreature)
    }
}
