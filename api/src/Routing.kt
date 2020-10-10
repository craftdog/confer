package com.confer.api


import com.confer.api.utilities.Roles
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception

fun Routing.routingRoot(roles: Roles) {
    participantRouting()
    modelRouting(roles)

}

fun Routing.modelRouting(roles: Roles) {
    route("/models") {

        route("/items") {

            post("/create") {

                call.respondText("create item")
            }

            get() {
                call.respondText("ALL ITEMS")
            }

            route("/{id}") {

                get() {
                    println("ITEM: " + call.parameters["id"])
                    call.respond(HttpStatusCode.Accepted, "get item: " + call.parameters["id"])
                }

                delete() {
                    println("delete ITEM: " + call.parameters["id"])
                    call.respond(HttpStatusCode.Accepted, "delete item: " + call.parameters["id"])
                }

                patch("/update") {
                    call.respondText("update item: " + call.parameters["id"]);
                }
            }
        }

        route("/roles") {

            post("/create") {

                val requestBody = call.receiveText()
                val bodyAsJson: JsonObject = JsonParser.parseString(requestBody).getAsJsonObject()
                if (bodyAsJson.has("isAdmin")
                    && bodyAsJson.has("roleName")) {


                    val roleName = bodyAsJson.get("roleName").asString
                    if (roleName.matches("^[a-zA-Z0-9]*$".toRegex())) {

                        val isAdmin = bodyAsJson.get("isAdmin").asBoolean
                        try {
                            roles.createRole(roleName, isAdmin)
                            call.respondText("Role Created Successfully")
                        } catch (e : Exception) {
                            call.response.status(HttpStatusCode.BadRequest)
                            call.respondText(e.message.toString())
                        }

                    } else {
                        call.response.status(HttpStatusCode.BadRequest)
                        call.respondText("roleName must be alphanumeric")
                    }

                } else {
                    call.response.status(HttpStatusCode.BadRequest)
                    call.respondText("Request must have isAdmin and roleName")
                }
            }

            get("/{roleName}") {
                call.respondText("get role: " + call.parameters["roleName"]);
            }

            patch("/{roleName}") {
                call.respondText("patch role: " + call.parameters["roleName"]);
            }

            delete("/{roleName}") {
                call.respondText("delete role: " + call.parameters["roleName"]);
            }
        }

        route("/items") {

            get {
                call.respondText("ALL ITEMS")
            }

            get("/{id}") {
                println("ITEM: " + call.parameters["id"])
                call.respond(HttpStatusCode.Accepted, "hello")
            }
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