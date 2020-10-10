package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.gson.JsonArray
import org.json.simple.JSONArray
import com.google.cloud.firestore.Firestore
import com.google.gson.JsonObject

fun documentExists(docRef : DocumentReference) : Boolean {
    val snapshot = docRef.get()
    val response = snapshot.get()
    return response.exists()
}

fun nameIsValid(name : String) : Boolean {
    return (name.matches("^[a-zA-Z0-9- ]*$".toRegex())
            && name.length != 0)
}

fun newUserIsValid(jsonBody : JsonObject, db : Firestore) : Boolean {
    return jsonBody.has("firstName")
            && jsonBody.has("lastName")
            && jsonBody.has("roleName")
            && nameIsValid(jsonBody.get("roleName").asString)
            && nameIsValid(jsonBody.get("firstName").asString)
            && nameIsValid(jsonBody.get("roleName").asString)
            && roleExists(jsonBody.get("roleName").asString, db)
}

fun itemNameIsValid(itemName : String) : Boolean {
    return true;
}

fun itemQuantityIsValid(itemQuantity : Long) : Boolean {
    return true;
}

fun itemRolesIsValid(itemRoles : List<String>) : Boolean {
    return true;
}

fun roleExists(roleName : String, db : Firestore) : Boolean {
    val docRef : DocumentReference = db.collection("roles").document(roleName)
    return documentExists(docRef)
}