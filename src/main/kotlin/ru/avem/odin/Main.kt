package ru.avem.odin

import javafx.stage.Stage
import ru.avem.kserialpooler.communication.PortDiscover
import ru.avem.odin.database.validateDB
import ru.avem.odin.view.MainView
import tornadofx.App

class Main: App(MainView::class) {
companion object {
    var isAppRunning = true
}
    override fun start(stage: Stage) {
        validateDB()
        super.start(stage)
    }

    override fun stop() {
        isAppRunning = false
        PortDiscover.isPortDiscover = false
        super.stop()
    }
}