package com.confer.api.routing


import com.confer.api.utilities.Items
import com.confer.api.utilities.Roles
import com.confer.api.utilities.Users
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.routingRoot(roles: Roles, items : Items, users : Users) {
    modelRouting(roles, items)
    usersRouting(users)
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
                processCreateRole(call, roles)
            }

            get("/{roleName}") {
                processGetRole(call, roles)
            }

            patch("/{roleName}") {
                processPatchRole(call, roles)
            }

            delete("/{roleName}") {
                processDeleteRole(call, roles)
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

fun Routing.usersRouting(users : Users) {
    route("/users") {

        post("/create") {
            processCreateUser(call, users)
        }

        get("/info/{id}") {
            val id = call.parameters["id"]
            call.respondText("id get")
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

