package com.thezayin.paksimdetails.space.data.local

import android.content.SharedPreferences

class GameLocalDataSource(private val sharedPreferences: SharedPreferences) {

    fun getHighScore(): Int? {
        return sharedPreferences.getInt("HIGH_SCORE", 0)
    }

    fun saveHighScore(score: Int) {
        sharedPreferences.edit().putInt("HIGH_SCORE", score).apply()
    }

    fun getUniqueId(): String? {
        return sharedPreferences.getString("UNIQUE_ID", null)
    }

    fun saveUniqueId(uniqueId: String) {
        sharedPreferences.edit().putString("UNIQUE_ID", uniqueId).apply()
    }

    fun savePendingScore(score: Int) {
        sharedPreferences.edit().putInt("PENDING_SCORE", score).apply()
    }

    fun getPendingScore(): Int? {
        return sharedPreferences.getInt("PENDING_SCORE", 0)
    }

    fun clearPendingScore() {
        sharedPreferences.edit().remove("PENDING_SCORE").apply()
    }
}