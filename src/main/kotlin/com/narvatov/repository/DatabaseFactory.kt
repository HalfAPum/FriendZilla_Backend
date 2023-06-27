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
import java.net.URI
import java.sql.DriverManager


object DatabaseFactory {

    fun init(config: ApplicationConfig) {
//        val dbUri = URI("")
//
//        val username: String = dbUri.getUserInfo().split(":").get(0)
//        val password: String = dbUri.getUserInfo().split(":").get(1)
//        val dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
//
//        return DriverManager.getConnection(dbUrl, username, password)

        Database.connect(hikari(config))
//        val driverClassName = config.property("storage.driverClassName").getString()
//        val jdbcURL = config.property("storage.jdbcURL").getString()
//        Database.connect(jdbcURL, driverClassName, user = "postgres", password = "admin")
//
        transaction {
            SchemaUtils.create(UserTable)
        }

        transaction {
            SchemaUtils.create(MatchTable)
        }
    }

    fun hikari(appConfig: ApplicationConfig): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = "jdbc:postgresql://postgres:EC0Z3CfXSG56qvW3ui4d@containers-us-west-176.railway.app:7328/railway?user=postgres&password=EC0Z3CfXSG56qvW3ui4d"
//            username = "postgres"
//            password = "EC0Z3CfXSG56qvW3ui4d"
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