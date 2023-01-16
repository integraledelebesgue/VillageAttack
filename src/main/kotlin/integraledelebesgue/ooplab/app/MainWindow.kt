package integraledelebesgue.ooplab.app

import javafx.application.Application
import javafx.stage.Stage
import kotlin.system.exitProcess

class MainWindow: Application() {

    override fun start(primaryStage: Stage) {

        primaryStage.setOnCloseRequest {
            exitProcess(0)
        }


    }

}