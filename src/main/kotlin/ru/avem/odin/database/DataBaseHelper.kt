package ru.avem.odin.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun validateDB() {
    Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
        SchemaUtils.create(Person)
    }

//    transaction {
//        PersonTable.new {
//            name = "222222"
//            surname = "1.1"
//            age = "1.2"
//            phoneNumber = "1.3"
//        }
//    }
}
