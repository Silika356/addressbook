package ru.avem.odin.database

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import java.time.*

object Person : IntIdTable() {
    val name = varchar("name", 32)
    val surname = varchar("surname", 32)
    val age = varchar("age", 32)
    val phoneNumber = varchar("phoneNumber", 32)
}

class PersonTable(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonTable>(Person)

    var name by Person.name
    var surname by Person.surname
    var age by Person.age
    var phoneNumber by Person.phoneNumber
    override fun toString(): String {
        return id.toString()
    }

}
