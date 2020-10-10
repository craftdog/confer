package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import java.lang.Exception
import java.lang.IllegalArgumentException

class Items(val db: Firestore) {

    fun createItem(itemName : String, itemQuantity : Long, itemRoles: List<String>) {
        val docRef : DocumentReference = db.collection("items").document(itemName)
        if (documentExists(docRef)) {
            throw Exception("Item Already Exists")
        }

        val roleMap = hashMapOf(
            "itemName" to itemName,
            "itemQuantity" to itemQuantity,
            "itemRoles" to itemRoles
        )

        docRef.set(roleMap)
    }

    fun deleteItem(itemName: String) {
        val docRef : DocumentReference = db.collection("items").document(itemName)
        if (!documentExists(docRef)) {
            throw Exception("Item Does not Exist")
        }

        docRef.delete();
    }

    fun getItem(itemId: String): MutableMap<String, Any>? {
        val docRef : DocumentReference = db.collection("items").document(itemId)
        if (documentExists(docRef)) {
            val document = db.collection("items").document(itemId).get()
            val snapshot = document.get()
            return snapshot.data
        } else {
            throw IllegalArgumentException("Items does not exist")
        }

    }

}