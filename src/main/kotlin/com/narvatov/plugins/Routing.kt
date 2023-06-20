package com.narvatov.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        route("/user") {
            post("/sign-up") {
                val body = call.receive<String>()
                call.respond(body)
            }

            delete("/delete-account/{id}") {
                val id = call.parameters["id"]
                call.respond(id.toString())
            }
        }

        get("/") {
            call.respondText("Hello World!")
        }
    }
}
