package com.narvatov.routing.user

import com.narvatov.data.model.user.User
import com.narvatov.data.response.OkResponse
import com.narvatov.data.response.SimpleResponse
import com.narvatov.repository.Repository
import com.narvatov.routing.API_VERSION
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.userRoute(
    repository: Repository,
) {
    route(USER) {
        post("/sign-up") {
            val user = try {
                call.receive<User>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Wrong sign up json input body. You must send only user id."))
                return@post
            }

            try {
                repository.addUser(user)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SimpleResponse(false, e.message ?: "Internal error inserting user."))
                return@post
            }

            call.respond(HttpStatusCode.OK, OkResponse())
        }

        delete("/delete-account/{id}") {
            val id = call.parameters["id"]

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "You must provide userId to delete."))
                return@delete
            }

            try {
                repository.deleteUser(id)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SimpleResponse(false, e.message ?: "Internal error deleting user."))
                return@delete
            }

            call.respond(HttpStatusCode.OK, OkResponse())
        }
    }
}

private const val USER = "$API_VERSION/user"