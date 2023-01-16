package integraledelebesgue.ooplab.element.creature

abstract class CreatureProperties(
    val health: Int,
    val damage: Int,
    val attackSpeed: Int,
    val movesPerTurn: Int,
    val fightStyle: FightStyle,
    val range: Int,
    val team: Team,  // Used to distinguish attackers from defenders. Expandable for multiple concurrent attacking teams, battle royale etc.
    val price: Int,
    //val behaviourProvider: Any  // TODO - introduce providers and change type
) {
    init {
        require(health > 0) {"Health must be positive, got $health"}
        require(damage >= 0) {"Damage must be non-negative, got $damage"}
        require(attackSpeed > 0) {"Attack speed must be positive, got $attackSpeed"}
        require(movesPerTurn > 0) {"Moves per turn must be positive, got $movesPerTurn"}
        require(range > 0) {"Attack range must be positive, got $range"}
        require(price >= 0) {"Price should be non-negative, got $price"}
    }
}

object WarewolfProperties : CreatureProperties(
    100,
    20,
    3,
    5,
    FightStyle.MELEE,
    1,
    Team.ATTACKERS,
    10
)

object MummyProperties : CreatureProperties(
    50,
    20,
    5,
    3,
    FightStyle.MELEE,
    2,
    Team.ATTACKERS,
    10
)

object ZombieProperties : CreatureProperties(
    80,
    30,
    3,
    5,
    FightStyle.MELEE,
    3,
    Team.ATTACKERS,
    10
)

object ArcherProperties : CreatureProperties(
    50,
    30,
    4,
    5,
    FightStyle.RANGED,
    10,
    Team.DEFENDERS,
    10
)

object CrossbowerProperties : CreatureProperties(
    50,
    50,
    2,
    3,
    FightStyle.RANGED,
    15,
    Team.DEFENDERS,
    10
)

object MageProperties : CreatureProperties(
    30,
    60,
    3,
    3,
    FightStyle.MAGIC,
    10,
    Team.DEFENDERS,
    10
)

