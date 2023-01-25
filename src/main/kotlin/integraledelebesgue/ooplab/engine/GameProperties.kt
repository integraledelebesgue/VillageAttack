package integraledelebesgue.ooplab.engine

object GameProperties{
    var gold: Int = 300
    val width: Int = 70
    val height: Int = 70

    val maxDefendersCount: Int = 10

    val castleMode: CastleMode = CastleMode.FancyCastle
    val defenderPositionsMode: DefenderPositionsMode = DefenderPositionsMode.RandomPositions
    val attackerPositionsMode: AttackerPositionsMode = AttackerPositionsMode.InteractivePositions
    val monsterAreaMode: MonsterAreaMode = MonsterAreaMode.BoundaryArea

    val defendersBehaviourMode: DefendersBehaviourMode = DefendersBehaviourMode.StationaryBehaviour
    val attackersBehaviourProvider: AttackersBehaviourProvider = AttackersBehaviourProvider.GreedyBehaviour

    init {
        require(gold > 0) {"Gold must be positive, got $gold"}
        require(width > 0) {"Map width must be positive, got $width"}
        require(height > 0) {"Map height must be positive, got $height"}
    }
}
