package integraledelebesgue.ooplab.engine

import integraledelebesgue.ooplab.map.generator.castle.CastleProvider
import integraledelebesgue.ooplab.map.generator.defenders.DefendersProvider


enum class CastleMode {
    SimpleCastle,
    FancyCastle;

    fun toProvider(gameProperties: GameProperties) {
        when(this) {
            SimpleCastle -> CastleProvider.SimpleCastleProvider
            FancyCastle -> CastleProvider.FancyCastleProvider
        }
    }
}

enum class DefenderPositionsMode {
    RandomPositions,
    OptimalPositions;

    fun toProvider(gameProperties: GameProperties) {
        when(this) {
            RandomPositions -> DefendersProvider.RandomPositionsDefendersProvider
            OptimalPositions -> DefendersProvider.OptimalPositionsDefendersProvider
        }
    }
}