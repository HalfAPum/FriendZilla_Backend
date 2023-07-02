package com.narvatov.data.table.user

import org.jetbrains.exposed.sql.Table

object UserTable : Table() {

    val id = varchar("id", 20)
    val latitude = double("latitude").nullable().default(null)
    val longitude = double("longitude").nullable().default(null)

    override val primaryKey = PrimaryKey(id)

}