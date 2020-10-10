package com.confer.api.routing

import com.confer.api.utilities.Roles
import com.confer.api.utilities.nameIsValid
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.lang.IllegalArgumentException

suspend fun processCreateRole(call : ApplicationCall, roles : Roles) {
    val requestBody = call.receiveText()
    val bodyAsJson: JsonObject = JsonParser.parseString(requestBody).getAsJsonObject()
    if (bodyAsJson.has("isAdmin")
        && bodyAsJson.has("roleName")) {


        val roleName = bodyAsJson.get("roleName").asString
        if (nameIsValid(roleName)) {
            val isAdmin = bodyAsJson.get("isAdmin").asBoolean
            try {
                roles.createRole(roleName, isAdmin)
                call.respondText("Role Created Successfully")
            } catch (e : IllegalArgumentException) {
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

suspend fun processGetRole(call : ApplicationCall, roles : Roles) {
    val roleName = call.parameters["roleName"]
    try {
        roles.getRole(roleName.toString())?.let { call.respond(it) }
    } catch (e : IllegalArgumentException) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText(e.message.toString())
    }
}

suspend fun processPatchRole(call : ApplicationCall, roles : Roles) {
    val roleName = call.parameters["roleName"]


}

suspend fun processDeleteRole(call : ApplicationCall, roles : Roles) {
    val roleName = call.parameters["roleName"]
    try {
        roles.deleteRole(roleName.toString())
        call.respondText("deleted successfully")
    } catch (e : IllegalArgumentException) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText(e.message.toString())
    }

}