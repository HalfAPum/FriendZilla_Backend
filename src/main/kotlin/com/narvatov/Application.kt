package com.narvatov

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.narvatov.plugins.*
import com.narvatov.repository.DatabaseFactory
import com.narvatov.routing.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    configureSerialization()
    configureRouting()
}
