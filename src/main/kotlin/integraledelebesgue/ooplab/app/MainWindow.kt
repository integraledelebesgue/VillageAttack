package integraledelebesgue.ooplab.app

import integraledelebesgue.ooplab.element.creature.CreatureFactory
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObjectFactory
import integraledelebesgue.ooplab.engine.GameEngine
import integraledelebesgue.ooplab.engine.GameProperties
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import kotlin.system.exitProcess

class MainWindow : Application() {

    private val scene = GridPane().apply {
        hgap = 1.0
        vgap = 1.0
        padding = Insets(10.0)

        for (x in 0 until GameProperties.width)
            for (y in 0 until GameProperties.height) {
                val emptyRectangle = Rectangle(25.0, 25.0)
                emptyRectangle.fill = Color.DARKSEAGREEN
                add(emptyRectangle, x, y)
            }

    }

    override fun start(primaryStage: Stage) {

        primaryStage.setOnCloseRequest {
            exitProcess(0)
        }

        GameEngine.gui = this
        val gameEngineThread = Thread(GameEngine)
        gameEngineThread.start()

        primaryStage.scene = Scene(scene)
        primaryStage.show()
    }

    fun drawPhysicalObjects() {
        PhysicalObjectFactory.globalStorage.forEach { (position, physicalObject) ->
            scene.add(
                run {
                    val objectRectangle = Rectangle(25.0, 25.0);
                    objectRectangle.fill = physicalObject.color;
                    objectRectangle
                },
                position.x,
                position.y
            )
        }
    }

    fun drawCreatures() {
        CreatureFactory.defendersStorage.forEach {
            scene.add(
                run {
                    val creatureCircle = Circle(12.5)
                    creatureCircle.fill= it.color
                    creatureCircle
                },
                it.position.x,
                it.position.y
            )
        }
    }

}