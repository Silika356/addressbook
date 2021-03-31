package ru.avem.odin

import javafx.stage.Stage
import ru.avem.odin.database.validateDB
import ru.avem.odin.view.MainView
import tornadofx.App

class Main: App(MainView::class) {

    override fun start(stage: Stage) {
        validateDB()
        super.start(stage)
    }
}