package com.confer.api

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream

fun getFirestore() : Firestore {
    val serviceAccount = FileInputStream("resources/service-account-file.json")

    val options = FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://confer-saas.firebaseio.com")
        .build()

    FirebaseApp.initializeApp(options)

    return FirestoreClient.getFirestore()
}


/*
 * Usage Example
 */

fun main(args: Array<String>) : Unit {

    // Init database object (instantiated from static auth files)
    val testDatabase = getFirestore()

    // Get an API Query object
    val items = testDatabase.collection("items").get()

    // Resolve the API Query (make the actual request)
    val itemsSnapshot = items.get()

    // Get documents from resolved query
    val documents = itemsSnapshot.documents;

    // Loop through documents ("items") and print out values
    for (document in documents) {
        println(document.getString("name") + ", " + document.getLong("default_quantity"))
    }
}
