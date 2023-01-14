package integraledelebesgue.ooplab.engine

object GameProperties{
    val gold: Int = 200
    val width: Int = 50
    val height: Int = 35

    val maxDefendersCount: Int = 99

    val wallMode: CastleMode = CastleMode.FancyCastle
    val defenderPositionsMode: DefenderPositionsMode = DefenderPositionsMode.RandomPositions

    init {
        require(gold > 0) {"Gold must be positive, got $gold"}
        require(width > 0) {"Map width must be positive, got $width"}
        require(height > 0) {"Map height must be positive, got $height"}
    }
}
