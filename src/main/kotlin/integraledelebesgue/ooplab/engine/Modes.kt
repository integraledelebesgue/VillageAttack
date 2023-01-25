package integraledelebesgue.ooplab.engine

import integraledelebesgue.ooplab.element.creature.behaviour.AttackersBehaviourProvider
import integraledelebesgue.ooplab.element.creature.behaviour.DefendersBehaviourProvider
import integraledelebesgue.ooplab.element.creature.behaviour.GreedyAttackersBehaviourProvider
import integraledelebesgue.ooplab.element.creature.behaviour.StationaryDefendersBehaviourProvider
import integraledelebesgue.ooplab.map.generator.areas.BoundaryAreaProvider
import integraledelebesgue.ooplab.map.generator.areas.MonsterAreaProvider
import integraledelebesgue.ooplab.map.generator.attackers.AttackersProvider
import integraledelebesgue.ooplab.map.generator.attackers.InteractiveAttackersProvider
import integraledelebesgue.ooplab.map.generator.castle.CastleProvider
import integraledelebesgue.ooplab.map.generator.castle.FancyCastleProvider
import integraledelebesgue.ooplab.map.generator.castle.SimpleCastleProvider
import integraledelebesgue.ooplab.map.generator.defenders.DefendersProvider
import integraledelebesgue.ooplab.map.generator.defenders.RandomPositionsDefendersProvider
import integraledelebesgue.ooplab.map.generator.defenders.OptimalPositionsDefendersProvider


enum class CastleMode {
    SimpleCastle,
    FancyCastle;

    fun toProvider(): CastleProvider {
        return when(this) {
            SimpleCastle -> SimpleCastleProvider
            FancyCastle -> FancyCastleProvider
        }
    }
}


enum class DefenderPositionsMode {
    RandomPositions,
    OptimalPositions;

    fun toProvider(): DefendersProvider {
        return when(this) {
            RandomPositions -> RandomPositionsDefendersProvider
            OptimalPositions -> OptimalPositionsDefendersProvider
        }
    }
}


enum class MonsterAreaMode {
    BoundaryArea;

    fun toProvider(): MonsterAreaProvider {
        return when(this) {
            BoundaryArea -> BoundaryAreaProvider
        }
    }
}


enum class DefendersBehaviourMode {
    StationaryBehaviour;

    fun toProvider(): DefendersBehaviourProvider {
        return when(this) {
            StationaryBehaviour -> StationaryDefendersBehaviourProvider
        }
    }
}


enum class AttackersBehaviourProvider {
    GreedyBehaviour;

    fun toProvider(): AttackersBehaviourProvider {
        return when(this) {
            GreedyBehaviour -> GreedyAttackersBehaviourProvider
        }
    }
}

enum class AttackerPositionsMode {
    InteractivePositions;

    fun toProvider(): AttackersProvider {
        return when(this) {
            InteractivePositions -> InteractiveAttackersProvider
        }
    }
}