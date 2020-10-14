package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.gson.JsonArray
import org.json.simple.JSONArray
import com.google.cloud.firestore.Firestore
import com.google.gson.JsonObject
import java.util.ArrayList

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
    return nameIsValid(itemName);
}

fun itemQuantityIsValid(itemQuantity : Long) : Boolean {
    return itemQuantity > 0;
}

fun itemRolesIsValid(itemRoles : List<String>) : Boolean {
    return itemRoles.isNotEmpty();
}

fun newItemIsValid(jsonBody: JsonObject, db: Firestore) : Boolean {

    if (jsonBody.has("itemRoles")) {
        val jsonItemRoles = jsonBody.get("itemRoles").asJsonArray
        val itemRoles: MutableList<String> = ArrayList()
        for (i in 0 until jsonItemRoles.size()) {
            itemRoles.add(jsonItemRoles.get(i).asString)
        }

        return jsonBody.has("itemName")
                && jsonBody.has("itemQuantity")
                && itemNameIsValid(jsonBody.get("itemName").asString)
                && itemQuantityIsValid(jsonBody.get("itemQuantity").asLong)
                && itemRolesIsValid(itemRoles)

    } else {
        return false;
    }
}

fun roleExists(roleName : String, db : Firestore) : Boolean {
    val docRef : DocumentReference = db.collection("roles").document(roleName)
    return documentExists(docRef)
}