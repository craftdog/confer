package com.confer.api.routing

import com.confer.api.utilities.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.*
import java.lang.IllegalArgumentException
import java.util.*


suspend fun processCreateItem(call : ApplicationCall, items : Items) {
    val requestBody = call.receiveText()
    val bodyAsJson: JsonObject = JsonParser.parseString(requestBody).getAsJsonObject()
    if (bodyAsJson.has("itemName")
                && bodyAsJson.has("itemQuantity")
                && bodyAsJson.has("itemRoles")
        ) {
        val itemName = bodyAsJson.get("itemName").asString
        val itemQuantity = bodyAsJson.get("itemQuantity").asLong

        val jsonItemRoles = bodyAsJson.get("itemRoles").asJsonArray
        val itemRoles: MutableList<String> = ArrayList()
        for (i in 0 until jsonItemRoles.size()) {
            itemRoles.add(jsonItemRoles.get(i).asString)
        }

        if (itemNameIsValid(itemName)
            && itemQuantityIsValid(itemQuantity)
            && itemRolesIsValid(itemRoles)
        ) {

            try {
                items.createItem(itemName, itemQuantity, itemRoles)
                call.respondText("Item Created Successfully")
            } catch (e : Exception) {
                call.response.status(HttpStatusCode.BadRequest)
                call.respondText(e.message.toString())
            }

        } else {
            call.response.status(HttpStatusCode.BadRequest)
            call.respondText("Invalid data for itemName, itemQuantity or itemRoles")
        }

    } else {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText("Request must have itemName, itemQuantity and itemRoles")
    }
}

suspend fun processGetItem(call : ApplicationCall, items : Items) {
    val itemId = call.parameters["id"]
    try {
        items.getItem(itemId.toString())?.let { call.respond(it) }
    } catch (e : IllegalArgumentException) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText(e.message.toString())
    }
}

suspend fun processGetAllItems(call : ApplicationCall, items : Items) {
    call.respond(items.getAllItems())
}

suspend fun processDeleteItem(call : ApplicationCall, items: Items) {
    val id = call.parameters["id"]
    try {
        items.deleteItem(id.toString())
        call.respondText("`$id` sucessfully deleted")
    } catch (e : Exception) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText("`$id` does not exist as an item")
    }
}