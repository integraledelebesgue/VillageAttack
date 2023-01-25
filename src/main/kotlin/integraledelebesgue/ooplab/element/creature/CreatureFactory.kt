package integraledelebesgue.ooplab.element.creature

import integraledelebesgue.ooplab.element.Vector2D


sealed interface CreatureFactory {

    val storage: MutableSet<Creature>

    fun create(position: Vector2D)

    companion object {
        fun attackerAt(position: Vector2D): Boolean {
            return position in attackersStorage.map { it.position }
        }

        fun defenderAt(position: Vector2D): Boolean {
            return position in defendersStorage.map { it.position }
        }

        val attackersStorage: MutableSet<Creature> = HashSet()
        val defendersStorage: MutableSet<Creature> = HashSet()

        suspend fun removeDeadCreatures() {
            Creature.factory.values
                .forEach { factory ->
                    factory.storage.removeIf { !it.isAlive }
                }
        }
    }
}

object WerewolfFactory : CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Werewolf(position, WarewolfProperties)
        storage.add(newCreature)
        CreatureFactory.attackersStorage.add(newCreature)
    }
}

object MummyFactory : CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Mummy(position, MummyProperties)
        WerewolfFactory.storage.add(newCreature)
        CreatureFactory.attackersStorage.add(newCreature)
    }
}

object ZombieFactory : CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Zombie(position, ZombieProperties)
        WerewolfFactory.storage.add(newCreature)
        CreatureFactory.attackersStorage.add(newCreature)
    }
}

object ArcherFactory : CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Archer(position, ArcherProperties)
        WerewolfFactory.storage.add(newCreature)
        CreatureFactory.defendersStorage.add(newCreature)
    }
}

object CrossbowerFactory : CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Crossbower(position, CrossbowerProperties)
        WerewolfFactory.storage.add(newCreature)
        CreatureFactory.defendersStorage.add(newCreature)
    }
}

object MageFactory : CreatureFactory {
    override val storage: MutableSet<Creature> = HashSet()

    override fun create(position: Vector2D) {
        val newCreature: Creature = Creature.Mage(position, MageProperties)
        WerewolfFactory.storage.add(newCreature)
        CreatureFactory.defendersStorage.add(newCreature)
    }
}
