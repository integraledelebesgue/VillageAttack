package integraledelebesgue.ooplab.app

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.Creature
import integraledelebesgue.ooplab.element.creature.CreatureFactory
import integraledelebesgue.ooplab.element.physicalobject.MonsterAreaFactory
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObject
import integraledelebesgue.ooplab.element.physicalobject.PhysicalObjectFactory
import integraledelebesgue.ooplab.element.physicalobject.WallFactory
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
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import kotlin.reflect.KClass
import kotlin.system.exitProcess

class MainWindow : Application() {

    private lateinit var goldLabel: Label
    private lateinit var buyState: KClass<out Creature>
    private lateinit var clickHandler: (MouseEvent) -> Unit

    private var attackerMarkers: MutableList<Shape> = mutableListOf()
    private var wallMarkers: List<Shape> = listOf()
    private var defenderMarkers: List<Shape> = listOf()

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

        val finishAttackerSetupButton = Button("Start battle").apply {
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
        val wallList: MutableList<Shape> = mutableListOf()

        PhysicalObjectFactory.globalStorage.forEach { (position, physicalObject) ->
            scene.add(
                run {
                    val objectRectangle = Rectangle(25.0, 25.0)

                    if(physicalObject::class == PhysicalObject.Wall::class)
                        wallList.add(objectRectangle)

                    objectRectangle.fill = physicalObject.color
                    objectRectangle
                },
                position.x,
                position.y
            )
        }

        wallMarkers = wallList.toList()
    }

    fun drawWalls() {
        wallMarkers.forEach { scene.children.remove(it) }

        wallMarkers = WallFactory.storage
            .filter { it.value.isAlive }
            .map {
                val objectRectangle = Rectangle(25.0, 25.0)
                objectRectangle.fill = it.value.color

                scene.add(
                    objectRectangle,
                    it.key.x,
                    it.key.y
                )

                objectRectangle
            }
            .toList()
    }

    fun drawDefenders() {
        defenderMarkers.forEach { scene.children.remove(it) }

        defenderMarkers = CreatureFactory.defendersStorage
            .filter { it.isAlive }
            .map {
                val creatureCircle = Circle(12.5)
                creatureCircle.fill = it.color

                scene.add(
                    creatureCircle,
                    it.position.x,
                    it.position.y
                )

                creatureCircle
            }
            .toList()
    }

    fun drawAttackers() {
        attackerMarkers.forEach { scene.children.remove(it) }

        attackerMarkers = CreatureFactory.attackersStorage
            .filter { it.isAlive }
            .map {
                val creatureCircle = Circle(12.5)
                creatureCircle.fill = Creature.properties[it::class]!!.color

                scene.add(
                    creatureCircle,
                    it.position.x,
                    it.position.y
                )

                creatureCircle
            }
            .toMutableList()
    }

    private fun drawAttacker(attackerClass: KClass<out Creature>, position: Vector2D) {
        val creatureCircle = Circle(12.5).apply {
            fill = Creature.properties[attackerClass]!!.color
        }

        scene.add(
            creatureCircle,
            position.x,
            position.y
        )

        attackerMarkers.add(creatureCircle)
    }

    fun drawAttack(attack: Pair<Vector2D, Vector2D>) {
        /*val attackLine = Line(
            attack.first.x.toDouble().times(25),
            attack.first.y.toDouble().times(25),
            attack.second.x.toDouble().times(25),
            attack.second.y.toDouble().times(25)
        )
        scene.add(attackLine, attack.first.x, attack.first.y)*/
        // Upcoming feature
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