package integraledelebesgue.ooplab.element.creature.behaviour

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.CreatureFactory
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObject
import integraledelebesgue.ooplab.element.physicalobject.WallFactory

sealed interface AttackersBehaviourProvider {

    suspend fun attack(): Sequence<Pair<Vector2D, Vector2D>>
    suspend fun move(): Sequence<Pair<Vector2D, Vector2D>>

}


object GreedyAttackersBehaviourProvider: AttackersBehaviourProvider {

    override suspend fun attack(): Sequence<Pair<Vector2D, Vector2D>> = sequence {
        CreatureFactory.attackersStorage
            .filter { it.isAlive }
            .forEach { attacker ->
                WallFactory.storage
                    .minBy { (position: Vector2D, wall: PhysicalObject?) ->
                        position.distanceTo(attacker.position)
                    }
                    .let {
                        if(it.key.distanceTo(attacker.position) <= attacker.range) {
                            it.value.takeDamage(attacker.damage)
                            println("$attacker hits ${it.value}")
                            yield(Pair(attacker.position, it.key))
                        }
                    }
            }
    }

    override suspend fun move(): Sequence<Pair<Vector2D, Vector2D>>  = sequence {
        CreatureFactory.attackersStorage
            .filter { it.isAlive }
            .forEach {
                val oldPosition = it.position
                val newPosition: Vector2D = it.moveProvider!!.generate(it)

                println("$it moves to $newPosition!")

                yield(
                    Pair(oldPosition, newPosition)
                )
            }

    }

}