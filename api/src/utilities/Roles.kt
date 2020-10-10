package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import java.lang.Exception
import java.lang.IllegalArgumentException

class Roles(val db : Firestore) {

    fun createRole(roleName : String, isAdmin : Boolean) {
        val docRef : DocumentReference = db.collection("roles").document(roleName)
        if (documentExists(docRef)) {
            throw IllegalArgumentException("Role Already Exists")
        }

        val roleMap = hashMapOf(
            "roleName" to roleName,
            "isAdmin" to isAdmin
        )

        docRef.set(roleMap)
        print(roleMap)

    }

    fun getRole(roleName: String): MutableMap<String, Any>? {
        val docRef : DocumentReference = db.collection("roles").document(roleName)
        if (documentExists(docRef)) {
            val document = db.collection("roles").document(roleName).get()
            val snapshot = document.get()
            return snapshot.data
        } else {
            throw IllegalArgumentException("Role does not exist")
        }

    }

    fun patchRole(roleName: String){

    }

    fun deleteRole(roleName: String) {
        val docRef : DocumentReference = db.collection("roles").document(roleName)
        if (documentExists(docRef)) {
            docRef.delete()
        } else {
            throw IllegalArgumentException("Role does not exist")
        }

    }


}