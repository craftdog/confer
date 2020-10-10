package com.confer.api.routing

import com.confer.api.utilities.Roles
import com.confer.api.utilities.Users
import com.confer.api.utilities.newUserIsValid
import com.google.cloud.firestore.DocumentReference
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.lang.IllegalArgumentException

suspend fun processCreateUser(call : ApplicationCall, users : Users) {
    val requestBody = call.receiveText()
    val bodyAsJson: JsonObject = JsonParser.parseString(requestBody).getAsJsonObject()

    if (newUserIsValid(bodyAsJson, users.db)) {

        val parameters : MutableMap<String, String> = HashMap()
        for (key in bodyAsJson.keySet()) {
            parameters.put(key, bodyAsJson.get(key).asString)
        }

        val id = users.createUser(parameters)
        call.respond(hashMapOf("id" to id).toString())
    } else {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText("User is invalid")
    }
}

suspend fun processGetUser(call : ApplicationCall, users : Users) {
    val userId = call.parameters["id"]
    try {
        users.getUser(userId.toString())?.let { call.respond(it) }
    } catch (e : IllegalArgumentException) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText(e.message.toString())
    }
}

suspend fun processDeleteUser(call : ApplicationCall, users : Users) {
    val id = call.parameters["id"]
    println(id)
    try {
        users.deleteUser(id.toString())
        call.respondText("deleted successfully")
    } catch (e : IllegalArgumentException) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText(e.message.toString())
    }

}