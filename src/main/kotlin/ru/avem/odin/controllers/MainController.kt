package ru.avem.odin.controllers

import ru.avem.kserialpooler.communication.Connection
import ru.avem.kserialpooler.communication.adapters.modbusrtu.ModbusRTUAdapter
import ru.avem.kserialpooler.communication.adapters.utils.ModbusRegister
import ru.avem.kserialpooler.communication.utils.SerialParameters
import ru.avem.odin.Constants.Companion.adapterName
import ru.avem.odin.Main.Companion.isAppRunning
import ru.avem.odin.view.MainView
import tornadofx.Controller
import tornadofx.runLater
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread

class MainController : Controller() {

    var btnState = 0
    val view: MainView by inject()

    val connection = Connection(
        adapterName = adapterName,
        serialParameters = SerialParameters(8, 0, 1, 38400),
        timeoutRead = 100,
        timeoutWrite = 100
    ).apply {
        connect()
    }

    val adapter = ModbusRTUAdapter(connection)


    fun request() {
        thread {
            if (btnState == 0) {
                runLater {
                    view.btnLamp.text = "Включить"
                }
            } else {
                runLater {
                    view.btnLamp.text = "Отключить"
                }
            }
            while (isAppRunning) {
                val time = adapter.readInputRegisters(2, 0x0202, 3).reversed().joinToString(":")

                btnState = adapter.readInputRegisters(2, 0x0205, 1).joinToString().toInt()

                runLater {
                    view.tfTime.text = time
                }
                if (time.last().toString().toInt() == 5 || time.last().toString().toInt() == 0) {
                    adapter.presetSingleRegister(2, 0x0205, ModbusRegister(1))
                } else {
                    adapter.presetSingleRegister(2, 0x0205, ModbusRegister(0))
                }
                sleep(200)
            }
            connection.disconnect()
        }
    }


    fun handleBtnLamp() {
        if (btnState == 0) {
            runLater {
                view.btnLamp.text = "Отключить"
            }
            adapter.presetSingleRegister(2, 0x0205, ModbusRegister(1))
        } else {
            runLater {
                view.btnLamp.text = "Включить"
            }
            adapter.presetSingleRegister(2, 0x0205, ModbusRegister(0))
        }
    }
}