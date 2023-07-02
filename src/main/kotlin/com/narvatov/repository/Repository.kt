package com.narvatov.repository

import com.narvatov.data.model.match.Match
import com.narvatov.data.model.user.AdditionalUserInfo
import com.narvatov.data.model.user.Location
import com.narvatov.data.model.user.NonMatchedFriendsRequest
import com.narvatov.data.model.user.User
import com.narvatov.data.table.match.MatchTable
import com.narvatov.data.table.user.UserTable
import com.narvatov.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

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

    suspend fun updateUserLocation(userId: String, location: Location) {
        dbQuery {
            UserTable.update(where = { UserTable.id.eq(userId) }) { userTable ->
                userTable[latitude] = location.latitude
                userTable[longitude] = location.longitude
            }
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

    suspend fun getUser(userId: String) = dbQuery {
        UserTable.select {
            UserTable.id.eq(userId)
        }.firstNotNullOfOrNull { it.rowToAdditionalUserInfo() }
    }

    suspend fun getNonMatchedFriends(request: NonMatchedFriendsRequest) = dbQuery {
        val userId = request.userId

        val matchedFriends = MatchTable.select {

            MatchTable.matcherId.eq(userId).or {
                MatchTable.responderId.eq(userId)
            }
        }.mapNotNull {
            it.rowToUserId(userId)
        }.toMutableList().apply {
            add(userId)
        }

        UserTable.select {
            UserTable.id.notInList(matchedFriends)
        }
            .limit(request.limit)
            .mapNotNull { it.rowToUser() }
    }

    private fun ResultRow.rowToUserId(userId: String): String {
        val matcherId = get(MatchTable.matcherId)
        val responderId = get(MatchTable.responderId)

        return if (matcherId == userId) responderId else matcherId
    }


    private fun ResultRow.rowToUser(): User {
        return User(
            id = get(UserTable.id),
        )
    }

    private fun ResultRow.rowToAdditionalUserInfo(): AdditionalUserInfo {
        return AdditionalUserInfo(
            id = get(UserTable.id),
            latitude = get(UserTable.latitude),
            longitude = get(UserTable.longitude),
        )
    }

}