package ru.avem.odin.view

import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.layout.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import ru.avem.odin.database.Person
import ru.avem.odin.database.PersonTable
import tornadofx.*

class MainView : View() {
    var tfName: TextField by singleAssign()
    var tfSurName: TextField by singleAssign()
    var tfAge: TextField by singleAssign()
    var tfPhoneNumber: TextField by singleAssign()

    var tableViewPerson: TableView<PersonTable> by singleAssign()
    var listOfPerson = transaction {
        PersonTable.all().toList().toObservable()
    }


    override val root = anchorpane {
        hbox(spacing = 16.0) {
            anchorpaneConstraints {
                leftAnchor = 16.0
                rightAnchor = 16.0
                topAnchor = 16.0
                bottomAnchor = 16.0
            }
            vbox(spacing = 16.0) {


                tableViewPerson = tableview(listOfPerson) {
                    column("Name", PersonTable::name).makeEditable()
                    column("Surname", PersonTable::surname).makeEditable()
                    column("Phone", PersonTable::phoneNumber).makeEditable()
                    column("Age", PersonTable::age).makeEditable()
                }
                hbox(spacing = 16.0) {
                    alignment = Pos.CENTER
                    button("Add") {
                        action {
                            var person = transaction {
                                PersonTable.new {
                                    name = tfName.text
                                    surname = tfSurName.text
                                    age = tfAge.text
                                    phoneNumber = tfPhoneNumber.text
                                }
                            }

                            listOfPerson.add(person)

                        }
                    }

                    button("Remove") {
                        prefWidth = 300.0
                        action {
                            print("hhhhhhhh")
                            if (!tableViewPerson.selectionModel.isEmpty) {

                                val person = transaction {
                                    Person.deleteWhere {
                                        Person.id eq tableViewPerson.selectedItem!!.id
                                    }
                                }
                                listOfPerson.clear()
                                listOfPerson.addAll(transaction {
                                    PersonTable.all().toList().toObservable()
                                })
                            } else {
                                println("kkkkkkkkkkkkkkk")
                            }
                        }
                    }
                }

            }
            vbox {
                label("Name")
                tfName = textfield {

                }
                label("Surname")
                tfSurName = textfield {

                }
                label("Age")
                tfAge = textfield {

                }
                label("PhoneNumber")
                tfPhoneNumber = textfield {

                }

            }
        }
    }
}
