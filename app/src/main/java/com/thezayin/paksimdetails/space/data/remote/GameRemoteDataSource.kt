package com.thezayin.paksimdetails.space.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class GameRemoteDataSource(private val firestore: FirebaseFirestore) {

    private val collectionName = "highscores"

    suspend fun fetchHighScore(uniqueId: String): Int {
        return try {
            val document = firestore.collection(collectionName).document(uniqueId).get().await()
            document.getLong("score")?.toInt() ?: 0
        } catch (e: Exception) {
            0 // Default score if fetching fails
        }
    }

    suspend fun uploadHighScore(uniqueId: String, score: Int) {
        try {
            val data = mapOf("score" to score)
            firestore.collection(collectionName).document(uniqueId).set(data).await()
        } catch (e: Exception) {
            e.printStackTrace() // Handle errors
        }
    }
}
