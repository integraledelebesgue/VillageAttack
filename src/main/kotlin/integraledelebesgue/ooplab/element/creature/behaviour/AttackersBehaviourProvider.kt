package integraledelebesgue.ooplab.element.creature.behaviour

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.CreatureFactory
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
                val toAttack = WallFactory.storage
                    .filter { (position, wall) ->
                        position.distanceTo(attacker.position) <= attacker.range
                    }
                    .minBy { (position, wall) ->
                        position.distanceTo(attacker.position)
                    }

                println("$attacker hits $toAttack")
                toAttack.value.takeDamage(attacker.damage)

                attacker.increaseAttack()

                yield(
                    Pair(attacker.position, toAttack.key)
                )
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