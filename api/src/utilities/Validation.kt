package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.gson.JsonArray
import org.json.simple.JSONArray

fun documentExists(docRef : DocumentReference) : Boolean {
    val snapshot = docRef.get()
    val response = snapshot.get()
    return response.exists()

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