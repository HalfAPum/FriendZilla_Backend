package com.narvatov.repository

import com.narvatov.data.table.match.MatchTable
import com.narvatov.data.table.user.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(config: ApplicationConfig) {
        Database.connect(hikari(config))

        transaction {
            SchemaUtils.createMissingTablesAndColumns(UserTable)

            SchemaUtils.createMissingTablesAndColumns(MatchTable)
        }
    }

    fun hikari(appConfig: ApplicationConfig): HikariDataSource {
        val config = HikariConfig().apply {
//            driverClassName = "org.postgresql.Driver"
            val dbHost = "containers-us-west-176.railway.app"
            val dbPort = "7328"
            val dbName = "railway"

            jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
            username = "postgres"
            password = "EC0Z3CfXSG56qvW3ui4d"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(
        block: () -> T
    ): T = withContext(Dispatchers.IO) {
        transaction { block() }
    }

}