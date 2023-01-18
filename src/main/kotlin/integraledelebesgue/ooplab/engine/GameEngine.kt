package integraledelebesgue.ooplab.engine

import integraledelebesgue.ooplab.app.MainWindow
import integraledelebesgue.ooplab.element.creature.CreatureFactory
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObjectFactory
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import kotlinx.coroutines.*
import java.lang.Runnable


object GameEngine: Runnable {

    lateinit var gui: MainWindow

    private var paused: Boolean = true
        @Synchronized set
        @Synchronized get

    private var active: Boolean = true
        @Synchronized set
        @Synchronized get

    init {
        print("Setup... ")
        setupGame()
        println("Done!")
    }

    @Synchronized
    fun toggleState() {
        paused.xor(true)
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

        println("Physical object generation finished")
        println("Generated ${PhysicalObjectFactory.globalStorage.size} objects")
        println(
            "Min x: ${PhysicalObjectFactory.globalStorage.keys.minBy { it.x }}\n" +
            "Max x: ${PhysicalObjectFactory.globalStorage.keys.maxBy { it.x }}\n" +
            "Min y: ${PhysicalObjectFactory.globalStorage.keys.minBy { it.y }}\n" +
            "Max y: ${PhysicalObjectFactory.globalStorage.keys.maxBy { it.y }}"
        )

        async {
            GameProperties.defenderPositionsMode
                .toProvider()
                .generate()
        }
            .await()

        println("Defender generation finished")
        println("Generated ${CreatureFactory.defendersStorage.size}")
    }

    override fun run() {
        try {
            nextTurn()
            do {
                if(paused) continue
                nextTurn()
            } while(active)
        }
        catch(ignored: InterruptedException) { }
        finally {
            println("Game has ended!")
        }

    }

    private fun nextTurn() = runBlocking {
        async {
            removeDeadCreatures()
            removeBrokenPhysicalObjects()
        }
            .await()

        async {
            proceedAttackers()
            proceedDefenders()
        }
            .await()

        Platform.runLater {
            gui.drawPhysicalObjects()
            gui.drawCreatures()
        }
    }

    private suspend fun proceedAttackers() {

    }

    private suspend fun proceedDefenders() {

    }

    private suspend fun removeDeadCreatures() {
        CreatureFactory.removeDeadCreatures()
    }

    private suspend fun removeBrokenPhysicalObjects() {
        PhysicalObjectFactory.removeBrokenPhysicalObjects()
    }

}