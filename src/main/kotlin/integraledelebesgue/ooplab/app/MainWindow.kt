package integraledelebesgue.ooplab.app

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.element.creature.CreatureFactory
import integraledelebesgue.ooplab.element.physicalobject.MonsterAreaFactory
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObjectFactory
import integraledelebesgue.ooplab.engine.GameEngine
import integraledelebesgue.ooplab.engine.GameProperties
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import java.lang.Thread.sleep
import kotlin.reflect.KClass
import kotlin.system.exitProcess

class MainWindow : Application() {

    private lateinit var goldLabel: Label
    private lateinit var buyState: KClass<out Creature>
    private lateinit var clickHandler: (MouseEvent) -> Unit

    private val physicalObjectIndicators: MutableMap<Vector2D, Shape> = hashMapOf()
    private val defenderIndicators: MutableMap<Vector2D, Shape> = hashMapOf()
    private val attackerIndicators: MutableMap<Vector2D, Shape> = hashMapOf()

    private val scene = GridPane().apply {
        hgap = 1.0
        vgap = 1.0
        padding = Insets(10.0)

        for (x in 0 until GameProperties.width) {
            for (y in 0 until GameProperties.height) {
                val emptyRectangle = Rectangle(25.0, 25.0)
                emptyRectangle.fill = Color.DARKSEAGREEN
                add(emptyRectangle, x, y)
            }
        }

        var rowIndex = 0
        val lastColumn: Int = GameProperties.width

        val difficultyLabel = Label("Difficulty:").apply {
            font = Font.font(null, FontWeight.BOLD, 13.5)
        }
        add(difficultyLabel, lastColumn, rowIndex)
        rowIndex++

        val difficultySlider = Slider(100.0, 500.0, 250.0)
        add(difficultySlider, lastColumn, rowIndex)
        rowIndex++

        fun displayGold() {
            goldLabel = Label(generateGoldText()).apply {
                font = Font.font(null, FontWeight.BOLD, 13.5)
            }
            add(goldLabel, lastColumn, rowIndex + 1)
            rowIndex += 2
        }

        val acceptButton = Button("Accept difficulty").apply {
            font = Font.font(null, FontWeight.SEMI_BOLD, 13.5)

            setOnAction {
                difficultySlider.isDisable = true
                this.isDisable = true
                GameProperties.gold = difficultySlider.value.toInt()
                displayGold()
                GameEngine.initialized = true
                startAttackerCollection()
            }
        }
        add(acceptButton, lastColumn, rowIndex)
        rowIndex++

        val buyZombieButton = Button("Place Zombies").apply {
            font = Font.font(null, FontWeight.SEMI_BOLD, 13.5)

            setOnAction {
                buyState = Creature.Zombie::class
            }
        }
        add(buyZombieButton, lastColumn, rowIndex)
        rowIndex++

        val buyMummyButton = Button("Place Mummies").apply {
            font = Font.font(null, FontWeight.SEMI_BOLD, 13.5)

            setOnAction {
                buyState = Creature.Mummy::class
            }
        }
        add(buyMummyButton, lastColumn, rowIndex)
        rowIndex++

        val buyWerewolfButton = Button("Place Werewolves").apply {
            font = Font.font(null, FontWeight.SEMI_BOLD, 13.5)

            setOnAction {
                buyState = Creature.Werewolf::class
            }
        }
        add(buyWerewolfButton, lastColumn, rowIndex)
        rowIndex++

        val finishAttackerSetupButton = Button("Finish").apply {
            font = Font.font(null, FontWeight.SEMI_BOLD, 13.5)

            setOnAction {
                buyZombieButton.isDisable = true
                buyMummyButton.isDisable = true
                buyWerewolfButton.isDisable = true
                stopAttackerCollection()
                GameEngine.paused = false
            }
        }
        add(finishAttackerSetupButton, lastColumn, rowIndex)
        rowIndex++
    }

    private fun generateGoldText(): String {
        return "Gold + ${GameProperties.gold}"
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
                    val objectRectangle = Rectangle(25.0, 25.0)
                    physicalObjectIndicators[position] = objectRectangle
                    objectRectangle.fill = physicalObject.color
                    objectRectangle
                },
                position.x,
                position.y
            )
        }
    }

    fun drawDefenders() {
        CreatureFactory.defendersStorage.forEach {
            scene.add(
                run {
                    val creatureCircle = Circle(12.5)
                    defenderIndicators[it.position] = creatureCircle
                    creatureCircle.fill = it.color
                    creatureCircle
                },
                it.position.x,
                it.position.y
            )
        }
    }

    private fun drawAttacker(attackerClass: KClass<out Creature>, position: Vector2D) {
        scene.add(
            run {
                val creatureCircle = Circle(12.5)
                defenderIndicators[position] = creatureCircle
                creatureCircle.fill = Creature.properties[attackerClass]!!.color
                creatureCircle
            },
            position.x,
            position.y
        )
    }

    fun changeAttackerPosition(positionChange: Pair<Vector2D, Vector2D>) {
        attackerIndicators[positionChange.first]?.let {
            attackerIndicators[positionChange.second] = it
            it.relocate(
                positionChange.second.x.toDouble(),
                positionChange.second.y.toDouble()
            )
        }

        attackerIndicators.keys.remove(positionChange.first)
    }

    fun drawAttack(attack: Pair<Vector2D, Vector2D>) {
        val attackLine = Line(
            attack.first.x.toDouble(),
            attack.first.y.toDouble(),
            attack.second.x.toDouble(),
            attack.second.y.toDouble()
        )

        scene.add(attackLine, attack.first.x, attack.first.y)
        sleep(100)
        scene.children.remove(attackLine)
    }

    private fun startAttackerCollection() {
        clickHandler = { it: MouseEvent ->
            val coordinates = Vector2D(it.x.div(25).toInt(), it.y.div(25).toInt())

            if (
                MonsterAreaFactory.isOccupied(coordinates) and
                (GameProperties.gold > 0) and
                !CreatureFactory.attackerAt(coordinates)
            ) {
                println("${buyState.simpleName} generated at $coordinates")
                GameProperties.attackerPositionsMode
                    .toProvider()
                    .generate(
                        buyState,
                        coordinates
                    )

                GameProperties.gold -= Creature.properties[buyState]!!.price
                goldLabel.text = generateGoldText()

                drawAttacker(buyState, coordinates)
            }
        }

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler)
    }

    private fun stopAttackerCollection() {
        scene.removeEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler)
    }

}