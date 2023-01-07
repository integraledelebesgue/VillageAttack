package integraledelebesgue.ooplab.element.creature

abstract class CreatureProperties(
    val health: Int,
    val damage: Int,
    val attacksPerTurn: Int,
    val movesPerTurn: Int,
    val fightStyle: FightStyle,
    val range: Int,
    val team: Team,  // Used to distinguish attackers from defenders. Expandable for multiple concurrent attacking teams, battle royale etc.
    val behaviourProvider: Any  // TODO - introduce providers and change type
)

object WarewolfProperties : CreatureProperties(
    100,
    20,
    3,
    5,
    FightStyle.MELEE,
    1,
    Team.ATTACKERS,
    0
)

object MummyProperties : CreatureProperties(
    50,
    20,
    5,
    3,
    FightStyle.MELEE,
    2,
    Team.ATTACKERS,
    0
)

object ZombieProperties : CreatureProperties(
    80,
    30,
    3,
    5,
    FightStyle.MELEE,
    3,
    Team.ATTACKERS,
    0
)

object ArcherProperties : CreatureProperties(
    50,
    30,
    4,
    5,
    FightStyle.RANGED,
    10,
    Team.DEFENDERS,
    0
)

object CrossbowerProperties : CreatureProperties(
    50,
    50,
    2,
    3,
    FightStyle.RANGED,
    15,
    Team.DEFENDERS,
    0
)

object MageProperties : CreatureProperties(
    30,
    60,
    3,
    3,
    FightStyle.MAGIC,
    10,
    Team.DEFENDERS,
    0
)

