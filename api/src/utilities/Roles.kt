package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import java.lang.Exception

class Roles(db : Firestore) {
    val db : Firestore = db

    fun createRole(roleName : String, isAdmin : Boolean) {
        val docRef : DocumentReference = db.collection("roles").document(roleName)
        if (documentExists(docRef)) {
            throw Exception("Role Already Exists")
        }

        val roleMap = hashMapOf(
            "roleName" to roleName,
            "isAdmin" to isAdmin
        )

        val roles  = docRef.set(roleMap)
        print(roleMap)

    }

    fun documentExists(docRef : DocumentReference) : Boolean {
        val snapshot = docRef.get()
        val response = snapshot.get()
        return response.exists()

    }
}