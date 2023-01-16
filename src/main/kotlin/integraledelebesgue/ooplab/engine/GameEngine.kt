package integraledelebesgue.ooplab.engine

import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import kotlinx.coroutines.*

object GameEngine {

    private var isPaused: Boolean = true
        @Synchronized set
        @Synchronized get

    init {
        setupGame()
    }

    @Synchronized
    fun toggleState() {
        isPaused.xor(true)
    }

    private fun setupGame() = runBlocking {
        async {
            GameProperties.castleMode
                .toProvider()
                .generate()

            GameProperties.monsterAreaMode
                .toProvider()
                .generate()
        }
            .await()

        async {
            GameProperties.defenderPositionsMode
                .toProvider()
                .generate()

            WallFactory.setShapes()
        }
            .await()
    }

    fun nextTurn() {

    }

    private fun proceedAttackers() {

    }

    private fun proceedDefenders() {

    }

    private fun removeDeadCreatures() {

    }

    private fun removeBrokenWalls() {

    }


}