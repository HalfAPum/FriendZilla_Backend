package com.narvatov.data.table.user

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {

    val id = varchar("id", 20)

    override val primaryKey = PrimaryKey(id)

}