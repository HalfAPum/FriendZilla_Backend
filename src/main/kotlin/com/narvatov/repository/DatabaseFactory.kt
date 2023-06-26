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
//        val driverClassName = config.property("storage.driverClassName").getString()
//        val jdbcURL = config.property("storage.jdbcURL").getString()
//        Database.connect(jdbcURL, driverClassName, user = "postgres", password = "admin")
//
//        transaction {
//            SchemaUtils.create(UserTable)
//        }
//
//        transaction {
//            SchemaUtils.create(MatchTable)
//        }
    }

    fun hikari(appConfig: ApplicationConfig): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = "jdbc:postgresql://db:5432/friendzilla_db?user=postgres&password=admin"
            username = "postgres"
            password = "admin"
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