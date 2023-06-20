package com.narvatov.routing.match

import com.narvatov.data.model.match.Match
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

fun Routing.matchRoute(
    repository: Repository,
) {
    route(MATCH) {
        post("/add") {
            val match = try {
                call.receive<Match>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Wrong match json input body. You must send only user matcherId and responderId values only."))
                return@post
            }

            try {
                repository.addMatch(match)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SimpleResponse(false, e.message ?: "Internal error inserting match."))
                return@post
            }

            call.respond(HttpStatusCode.OK, OkResponse())
        }
    }
}

private const val MATCH = "$API_VERSION/match"