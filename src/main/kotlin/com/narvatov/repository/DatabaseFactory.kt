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
        val driverClassName = config.property("storage.driverClassName").getString()
        val jdbcURL = config.property("storage.jdbcURL").getString()
        Database.connect(jdbcURL, driverClassName, user = "postgres", password = "admin")

        transaction {
            SchemaUtils.create(UserTable)
        }

        transaction {
            SchemaUtils.create(MatchTable)
        }
    }

//    fun hikari(): HikariDataSource {
//        val config = HikariConfig()
//        config.driverClassName = "org.postgresql.Driver"
//        config.jdbcUrl = "jdbc:postgresql://db:5433/friendzilla_db?user=postgres&password=admin"
//        config.username = "postgres"
//        config.password = "admin"
//        config.maximumPoolSize = 3
//        config.isAutoCommit = false
//        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//        config.validate()
//
//        return HikariDataSource(config)
//    }

    suspend fun <T> dbQuery(
        block: () -> T
    ): T = withContext(Dispatchers.IO) {
        transaction { block() }
    }

}