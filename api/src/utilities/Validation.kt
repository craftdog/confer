package com.confer.api.utilities

import com.google.cloud.firestore.DocumentReference

fun documentExists(docRef : DocumentReference) : Boolean {
    val snapshot = docRef.get()
    val response = snapshot.get()
    return response.exists()

}