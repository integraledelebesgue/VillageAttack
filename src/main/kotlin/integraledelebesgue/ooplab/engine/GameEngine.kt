package integraledelebesgue.ooplab.engine

import integraledelebesgue.ooplab.app.MainWindow
import integraledelebesgue.ooplab.element.creature.CreatureFactory
import javafx.application.Platform
import kotlinx.coroutines.*
import java.lang.Thread.sleep


object GameEngine: Runnable {

    lateinit var gui: MainWindow

    var initialized: Boolean = false
        @Synchronized set(state) { if(!field) field = state }
        @Synchronized get

    var paused: Boolean = true
        @Synchronized set
        @Synchronized get

    private var active: Boolean = true

    private fun setupMap() = runBlocking {
        async {
            GameProperties.castleMode
                .toProvider()
                .generate()

            GameProperties.monsterAreaMode
                .toProvider()
                .generate()
        }
            .await()
    }

    private fun setupDefenders() = runBlocking {
        async {
            GameProperties.defenderPositionsMode
                .toProvider()
                .generate()
        }
            .await()
    }

    override fun run() {
        try {
            setupMap()

            Platform.runLater {
                gui.drawPhysicalObjects()
            }

            while(!initialized) {
                sleep(100)
            }

            setupDefenders()

            Platform.runLater {
                gui.drawDefenders()
            }

            while(paused) {
                sleep(100)
            }

            println("Battle begins!")

            simulationStage()
        }
        catch(ignored: Exception) { }
        finally {
            println("Game has ended!")
        }

    }

    @Throws(InterruptedException::class)
    private fun simulationStage() {
        do {
            println("Next turn!")
            if(paused) continue
            nextTurn()
            sleep(500)
        } while(active)
    }

    private fun nextTurn() = runBlocking {
        async {
            checkForWin()
            checkForStale()
            proceedDefenders()
            proceedAttackers()
        }
            .await()

        Platform.runLater {
            gui.drawWalls()
            gui.drawAttackers()
            gui.drawDefenders()
        }
    }

    private suspend fun proceedAttackers() {
        for(positionChange in GameProperties.attackersBehaviourProvider.toProvider().move())
            apply {}

        for(attack in GameProperties.attackersBehaviourProvider.toProvider().attack())
            apply {}
    }

    private suspend fun proceedDefenders() {
        for(attack in GameProperties.defendersBehaviourMode.toProvider().attack())
            Platform.runLater{
                gui.drawAttack(attack)
            }
    }

    private suspend fun checkForStale() {
        if(CreatureFactory.attackersStorage.none { it.isAlive }) {
            println("Defenders won!")
            active = false
        }
    }

    private suspend fun checkForWin() {
        if(CreatureFactory.attackersStorage.any { it.position.distanceTo(GameProperties.castleMode.toProvider().centre) <= 2 }) {
            println("Monsters won!")
            active = false
        }
    }

}