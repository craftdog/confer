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
                processGetAllItems(call, items)
            }

            route("/{id}") {

                get() {
                    processGetItem(call, items)
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

            get() {
                processGetAllRoles(call, roles)
            }

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

    }


}

fun Routing.usersRouting(users : Users) {
    route("/users") {

        post("/create") {
            processCreateUser(call, users)
        }

        get("/info/{id}") {
            processGetUser(call, users)
        }

        delete("/remove/{id}") {
            processDeleteUser(call, users)
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

