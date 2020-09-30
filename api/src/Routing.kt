package com.confer.api

import io.ktor.application.call
import io.ktor.http.*
import io.ktor.locations.Location
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.routingRoot() {
    participantRouting()
    modelRouting()
}

fun Routing.modelRouting() {
    route("/models/items") {
        get {
            call.respondText("ALL ITEMS")
        }

        get("/{id}") {
            println("ITEM: " + call.parameters["id"])
            call.respond(HttpStatusCode.Accepted, "hello")
        }
    }
}

fun Routing.participantRouting() {
    route("/participant") {

        get("/info/{id}") {
            val id = call.parameters["id"]
            call.respondText("id get")
        }

        post("/create") {
            call.respondText("{\"test\":\"result\"}")
        }

        delete("/remove/{id}") {
            call.respondText("Remove")
        }

        route("/items") {
            get("/{id}") {
                val id: String? = call.parameters["id"]
                if (id != null) {
                    call.respondText("participant items id" + id)

                } else {
                    call.respondText("participant items id")
                }

            }

            patch("reduce/{id}/{itemId}") {
                println(call.parameters["id"] + "  1")
                println(call.parameters["itemId"] + "  2")
                call.respondText("{\"test\":\"reduce\"}")
            }

            patch("increase/{id}/{itemId}") {
                println(call.parameters["id"] + "  1")
                println(call.parameters["itemId"] + "  2")
                call.respondText("{\"test\":\"increase\"}")
            }
        }
    }
}