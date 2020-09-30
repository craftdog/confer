package com.confer.api

import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.response.respondText
import io.ktor.routing.*

fun Routing.routingRoot() {
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

    route("/models/items") {
        get {
            call.respondText("ALL ITEMS")
        }

        get("/{id}") {
            println("ITEM: " + call.parameters["id"])
            call.respondText("specific item")
        }
    }

    /*
    method(HttpMethod.Get) {
        route("/participant") {

        }
    }
    get("/test") {
        call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)

    }
    get("/participant/info/{id}") {
        val id = call.parameters["id"]
        println(id)
    }

    get("/participant") {

    }





    get("/html-dsl") {
        call.respondHtml {
            body {
                h1 { +"HTML" }
                ul {
                    for (n in 1..10) {
                        li { +"$n" }
                    }
                }
            }
        }
    }

    // Static feature. Try to access `/static/ktor_logo.svg`
    static("/static") {
        resources("static")
    }

    get<MyLocation> {
        call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
    }
    // Register nested routes
    get<Type.Edit> {
        call.respondText("Inside $it")
    }
    get<Type.List> {
        call.respondText("Inside $it")
    }

    get("/session/increment") {
        val session = call.sessions.get<MySession>() ?: MySession()
        call.sessions.set(session.copy(count = session.count + 1))
        call.respondText("Counter is ${session.count}. Refresh to increment.")
    }

    webSocket("/myws/echo") {
        send(Frame.Text("Hi from server"))
        while (true) {
            val frame = incoming.receive()
            if (frame is Frame.Text) {
                send(Frame.Text("Client said: " + frame.readText()))
            }
        }
    }

    get("/json/gson") {
        call.respond(mapOf("hello" to "world"))
    }

     */
}



@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@Location("/type/{name}") data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}