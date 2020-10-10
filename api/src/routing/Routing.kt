package com.confer.api.routing


import com.confer.api.utilities.Items
import com.confer.api.utilities.Roles
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception

fun Routing.routingRoot(roles: Roles, items : Items) {
    participantRouting()
    modelRouting(roles, items)
}

fun Routing.modelRouting(roles: Roles, items : Items) {
    route("/models") {

        route("/items") {

            post("/create") {
                processCreateItem(call, items)
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
                    processDeleteItem(call, items)
                }

                patch("/update") {
                    call.respondText("update item: " + call.parameters["id"]);
                }
            }
        }

        route("/roles") {

            post("/create") {
                createRole(call, roles)
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

