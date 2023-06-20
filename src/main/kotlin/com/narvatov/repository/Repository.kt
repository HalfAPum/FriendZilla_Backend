package com.narvatov.repository

import com.narvatov.data.model.match.Match
import com.narvatov.data.model.user.User
import com.narvatov.data.table.match.MatchTable
import com.narvatov.data.table.user.UserTable
import com.narvatov.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert

class Repository {

    suspend fun addUser(user: User) {
        dbQuery {
            UserTable.insert { table ->
                table[id] = user.id
            }
        }
    }

    suspend fun deleteUser(id: String) {
        dbQuery {
            UserTable.deleteWhere { UserTable.id.eq(id) }
        }
    }

    suspend fun addMatch(match: Match) {
        dbQuery {
            MatchTable.insert { table ->
                table[matcherId] = match.matcherId
                table[responderId] = match.responderId
            }
        }
    }

}