package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import java.lang.IllegalArgumentException

class Users(val db : Firestore) {

    fun createUser(parameters : MutableMap<String, String>) : String {
        val docRef : DocumentReference = db.collection("users").document()
        docRef.set(parameters as Map<String, Any>)
        return docRef.id
    }
}