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

    fun deleteUser(userId: String) {
        val docRef : DocumentReference = db.collection("users").document(userId)
        if (documentExists(docRef)) {
            docRef.delete()
        } else {
            throw IllegalArgumentException("User does not exist")
        }

    }

    fun getUser(userId: String): MutableMap<String, Any>? {
        val docRef : DocumentReference = db.collection("users").document(userId)
        if (documentExists(docRef)) {
            val document = db.collection("users").document(userId).get()
            val snapshot = document.get()
            return snapshot.data
        } else {
            throw IllegalArgumentException("User does not exist")
        }

    }
}