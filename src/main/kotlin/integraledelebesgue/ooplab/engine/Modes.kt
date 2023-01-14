package integraledelebesgue.ooplab.engine

import integraledelebesgue.ooplab.map.generator.defenders.OptimalDefenderPositionsProvider
import integraledelebesgue.ooplab.map.generator.defenders.RandomDefenderPositionsProvider
import integraledelebesgue.ooplab.map.generator.castle.FancyCastleProvider
import integraledelebesgue.ooplab.map.generator.castle.SimpleCastleProvider

enum class CastleMode {
    SimpleCastle,
    FancyCastle;

    fun toProvider(gameProperties: GameProperties) {
        when(this) {
            SimpleCastle -> SimpleCastleProvider
            FancyCastle -> FancyCastleProvider
        }
    }
}

enum class DefenderPositionsMode {
    RandomPositions,
    OptimalPositions;

    fun toProvider(gameProperties: GameProperties) {
        when(this) {
            RandomPositions -> RandomDefenderPositionsProvider
            OptimalPositions -> OptimalDefenderPositionsProvider
        }
    }
}