package integraledelebesgue.ooplab.element.creature

import javafx.scene.paint.Color

abstract class CreatureProperties(
    val health: Int,
    val damage: Int,
    val attackSpeed: Int,
    val movesPerTurn: Int,
    val fightStyle: FightStyle,
    val range: Int,
    val team: Team,  // Used to distinguish attackers from defenders. Expandable for multiple concurrent attacking teams, battle royale etc.
    val price: Int,
    val color: Color
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
    10,
    Color.BROWN
)

object MummyProperties : CreatureProperties(
    50,
    20,
    5,
    3,
    FightStyle.MELEE,
    2,
    Team.ATTACKERS,
    10,
    Color.BISQUE
)

object ZombieProperties : CreatureProperties(
    80,
    30,
    3,
    5,
    FightStyle.MELEE,
    3,
    Team.ATTACKERS,
    10,
    Color.DARKKHAKI
)

object ArcherProperties : CreatureProperties(
    50,
    30,
    4,
    5,
    FightStyle.RANGED,
    10,
    Team.DEFENDERS,
    15,
    Color.ALICEBLUE
)

object CrossbowerProperties : CreatureProperties(
    50,
    50,
    2,
    3,
    FightStyle.RANGED,
    15,
    Team.DEFENDERS,
    30,
    Color.PALEVIOLETRED
)

object MageProperties : CreatureProperties(
    30,
    60,
    3,
    3,
    FightStyle.MAGIC,
    10,
    Team.DEFENDERS,
    50,
    Color.CORAL
)

