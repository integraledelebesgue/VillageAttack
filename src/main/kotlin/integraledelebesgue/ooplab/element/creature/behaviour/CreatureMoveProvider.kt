package integraledelebesgue.ooplab.element.creature.behaviour

import integraledelebesgue.ooplab.element.Direction
import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import integraledelebesgue.ooplab.engine.GameProperties

sealed interface CreatureMoveProvider {

    fun generate(creature: Creature): Vector2D

}


object GreedyCreatureMoveProvider: CreatureMoveProvider {

    @Synchronized
    override fun generate(creature: Creature): Vector2D {
        val newPosition = creature.position + Direction.values()
            .minBy {
                (it.unitVector + creature.position)
                    .euclideanDistanceTo(
                        GameProperties.castleMode.toProvider().centre
                    )
            }
            .unitVector

        if(WallFactory.isOccupied(newPosition))
            return creature.position

        return newPosition
    }

}