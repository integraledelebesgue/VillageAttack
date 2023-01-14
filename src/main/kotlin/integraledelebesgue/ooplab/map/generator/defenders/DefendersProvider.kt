package integraledelebesgue.ooplab.map.generator.defenders

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import kotlin.reflect.KClass

sealed class DefendersProvider {

    abstract fun generate()

    fun placeDefenders(defenderPositions: Map<KClass<out Creature>, List<Vector2D>>) {
        defenderPositions.forEach { (creatureClass, positions) ->
            run {
                positions.forEach { Creature.factory[creatureClass]!!.create(it) }
            }
        }
    }


    object RandomPositionsDefendersProvider : DefendersProvider() {

        override fun generate() {
            placeDefenders(
                distributePositions(
                    DefendersCountProvider.KnapsackDefendersCountProvider.generate()
                )
            )
        }

        private fun distributePositions(counts: List<Int>): Map<KClass<out Creature>, List<Vector2D>> {
            val positions: MutableList<Vector2D> = WallFactory
                .storage
                .keys
                .shuffled()
                .toMutableList()

            return Creature
                .defenders
                .zip(counts)
                .associate {
                    Pair(
                        it.first,
                        (1..it.second)
                            .map { positions.removeFirst() }
                    )
                }
        }

    }


    object OptimalPositionsDefendersProvider : DefendersProvider() {
        override fun generate() {
            TODO("Not yet implemented")
        }
    }

}






