package com.thezayin.paksimdetails.game.data.local

import android.content.SharedPreferences

interface MemoryGameLocalDataSource {
    suspend fun saveHighscore(highscore: Int)
    suspend fun getHighscore(): Int
}

class MemoryGameLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : MemoryGameLocalDataSource {
    override suspend fun saveHighscore(highscore: Int) {
        sharedPreferences.edit().putInt("Highscore", highscore).apply()
    }

    override suspend fun getHighscore(): Int {
        return sharedPreferences.getInt("Highscore", 0)
    }
}