package integraledelebesgue.ooplab.element.creature.behaviour

import integraledelebesgue.ooplab.element.Direction
import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import integraledelebesgue.ooplab.engine.GameProperties
import integraledelebesgue.ooplab.map.generator.castle.CastleProvider

sealed interface CreatureMoveProvider {

    fun generate(creature: Creature): Vector2D

}


object GreedyCreatureMoveProvider: CreatureMoveProvider {

    override fun generate(creature: Creature): Vector2D {
        val newPosition = creature.position + Direction.values()
            .minBy {
                (it.unitVector + creature.position).distanceTo(
                    GameProperties.castleMode.toProvider().centre
                )
            }
            .unitVector
            //.times(creature.movesPerTurn)

        if(WallFactory.isOccupied(newPosition))
            return creature.position

        creature.position = newPosition
        return newPosition
    }

}