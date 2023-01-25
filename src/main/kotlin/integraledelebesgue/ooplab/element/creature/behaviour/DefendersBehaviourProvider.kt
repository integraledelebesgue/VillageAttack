package integraledelebesgue.ooplab.element.creature.behaviour

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.CreatureFactory


sealed interface DefendersBehaviourProvider {

    suspend fun attack(): Sequence<Pair<Vector2D, Vector2D>>

}


object StationaryDefendersBehaviourProvider: DefendersBehaviourProvider {

    override suspend fun attack(): Sequence<Pair<Vector2D, Vector2D>> = sequence {
        CreatureFactory.defendersStorage
            .filter { it.isAlive }
            .forEach { defender ->
                val toAttack = CreatureFactory.attackersStorage
                    .minBy {it.position.distanceTo(defender.position)}
                    .let {
                        if(it.isAlive and (it.position.distanceTo(defender.position) <= defender.range)) {
                            it.takeDamage(defender.damage)
                            yield(Pair(defender.position, it.position))
                            println("$defender hits $it")
                        }
                    }
            }
    }

}