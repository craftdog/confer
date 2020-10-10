package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import java.lang.Exception

class Roles(val db : Firestore) {

    fun createRole(roleName : String, isAdmin : Boolean) {
        val docRef : DocumentReference = db.collection("roles").document(roleName)
        if (documentExists(docRef)) {
            throw Exception("Role Already Exists")
        }

        val roleMap = hashMapOf(
            "roleName" to roleName,
            "isAdmin" to isAdmin
        )

        docRef.set(roleMap)
        print(roleMap)

    }


}