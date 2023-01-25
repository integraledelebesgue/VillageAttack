package integraledelebesgue.ooplab.map.generator.defenders

import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.engine.GameProperties
import org.chocosolver.solver.Model

sealed interface DefendersCountProvider {

    fun generate(): List<Int>

}


object KnapsackDefendersCountProvider : DefendersCountProvider {

    private val model: Model = Model("Defenders")

    private val count = model.intVarArray("count", Creature.defenders.size, 0, GameProperties.maxDefendersCount)

    private val cost = Creature.defenders
        .map {Creature.properties[it]!!.price}
        .toIntArray()

    private val damage = Creature.defenders
        .map {Creature.properties[it]!!.damage}
        .toIntArray()

    private val total_damage = model.intVar("total_damage", 0, 999, true)

    init {
        model.scalar(count, cost, "<=", GameProperties.gold).post()
        model.scalar(count, damage, "=", total_damage).post()

        model.setObjective(true, total_damage)
    }

    override fun generate(): List<Int> {
        return run {
            var bestSolution: List<Int> = (0 until Creature.defenders.size).map {0}
            while (model.solver.solve()) {
                bestSolution = count.map { it.value }
            }
            bestSolution
        }
    }
}