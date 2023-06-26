package com.narvatov.routing

import com.narvatov.repository.Repository
import com.narvatov.routing.match.matchRoute
import com.narvatov.routing.user.userRoute
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    val repository = Repository()

    routing {
        userRoute(repository)
        matchRoute(repository)

        get("/") {
            call.respondText("Hello World!")
        }
    }
}

const val API_VERSION = "/v1"