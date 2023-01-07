package integraledelebesgue.ooplab.element.creature

import integraledelebesgue.ooplab.element.Vector2D


sealed interface CreatureFactory {

    val storage: MutableSet<Creature>

    fun create(position: Vector2D)

    companion object {
        val attackersStorage: MutableSet<Creature> = HashSet()
        val defendersStorage: MutableSet<Creature> = HashSet()
    }
}

object WarewolfFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Warewolf(position, WarewolfProperties)
        storage.add(newCreature)
        CreatureFactory.attackersStorage.add(newCreature)
    }
}

object MummyFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Mummy(position, MummyProperties)
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.attackersStorage.add(newCreature)
    }
}

object ZombieFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Zombie(position, ZombieProperties)
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.attackersStorage.add(newCreature)
    }
}

object ArcherFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Archer(position, ArcherProperties)
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.defendersStorage.add(newCreature)
    }
}

object CrossbowerFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Crossbower(position, CrossbowerProperties)
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.defendersStorage.add(newCreature)
    }
}

object MageFactory: CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Mage(position, MageProperties)
        WarewolfFactory.storage.add(newCreature)
        CreatureFactory.defendersStorage.add(newCreature)
    }
}
