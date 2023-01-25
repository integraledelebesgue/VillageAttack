package integraledelebesgue.ooplab.element.creature.behaviour

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.CreatureFactory


sealed interface DefendersBehaviourProvider {

    suspend fun attack(): Sequence<Pair<Vector2D, Vector2D>>

}


object StationaryDefendersBehaviourProvider: DefendersBehaviourProvider {

    override suspend fun attack(): Sequence<Pair<Vector2D, Vector2D>> = sequence {
        CreatureFactory.defendersStorage
            .filter { it.hasAttack and it.isAlive }
            .forEach { defender ->
                val toAttack = CreatureFactory.defendersStorage
                    .filter { it.isAlive and (it.position.distanceTo(defender.position) <= defender.range) }
                    .minBy {it.position.distanceTo(defender.position)}

                defender.increaseAttack()

                toAttack.takeDamage(defender.damage)

                yield(
                    Pair(defender.position, toAttack.position)
                )
            }
    }

}