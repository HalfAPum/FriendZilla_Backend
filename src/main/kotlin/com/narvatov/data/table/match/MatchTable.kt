package com.narvatov.data.table.match

import com.narvatov.data.table.user.UserTable
import org.jetbrains.exposed.sql.Table

object MatchTable : Table() {

    val matcherId = varchar("matcher_id", 20).references(UserTable.id)
    val responderId = varchar("responder_id", 20).references(UserTable.id)

    override val primaryKey = PrimaryKey(matcherId, responderId)

}