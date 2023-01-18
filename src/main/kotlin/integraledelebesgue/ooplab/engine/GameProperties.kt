package integraledelebesgue.ooplab.engine

object GameProperties{
    val gold: Int = 200
    val width: Int = 50
    val height: Int = 50

    val maxDefendersCount: Int = 5

    val castleMode: CastleMode = CastleMode.FancyCastle
    val defenderPositionsMode: DefenderPositionsMode = DefenderPositionsMode.RandomPositions
    val monsterAreaMode: MonsterAreaMode = MonsterAreaMode.BoundaryArea

    init {
        require(gold > 0) {"Gold must be positive, got $gold"}
        require(width > 0) {"Map width must be positive, got $width"}
        require(height > 0) {"Map height must be positive, got $height"}
    }
}
