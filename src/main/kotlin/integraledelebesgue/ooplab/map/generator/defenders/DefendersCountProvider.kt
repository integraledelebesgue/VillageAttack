package integraledelebesgue.ooplab.map.generator.defenders

import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.engine.GameProperties
import org.chocosolver.solver.Model
import org.chocosolver.solver.Solver
import org.chocosolver.solver.search.strategy.Search
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMiddle
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail
import org.chocosolver.solver.search.strategy.selectors.variables.Smallest
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelectorWithTies

sealed class DefendersCountProvider {

    abstract fun generate(): List<Int>

    object KnapsackDefendersCountProvider : DefendersCountProvider() {

        private val model: Model = Model("Defenders")
        private var solver: Solver

        private val count = model.intVarArray("count", Creature.defenders.size, 0, GameProperties.maxDefendersCount)

        private val cost = Creature.properties
            .filter { it.key in Creature.defenders }
            .values
            .map { it.price }
            .toIntArray()

        private val damage = Creature.properties
            .filter { it.key in Creature.defenders }
            .values
            .map { it.damage }
            .toIntArray()

        private val total_cost =
            model.intVar("total_cost", 0, GameProperties.maxDefendersCount * GameProperties.gold, true)

        private val total_damage = model.intVar("total_damage", 0, 9999, true)

        init {
            // Set all counts to 0
            count.fill(model.intVar(0))

            // Constraints:
            model.scalar(count, cost, "<=", GameProperties.gold).post()
            model.scalar(count, cost, "=", total_cost).post()
            model.scalar(count, damage, "=", total_damage)

            model.setObjective(true, total_damage)

            solver = model.solver

            solver.setSearch(
                Search.intVarSearch(
                    VariableSelectorWithTies(
                        FirstFail(model), Smallest()
                    ), IntDomainMiddle(false)
                )
            )
        }

        override fun generate(): List<Int> {
            while(solver.solve()) { /* Repeat until no further improvements are possible */ }
            return count.map{it.value}
        }

    }

}