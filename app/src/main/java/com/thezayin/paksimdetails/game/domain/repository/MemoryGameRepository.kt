package com.thezayin.paksimdetails.game.domain.repository

import com.thezayin.paksimdetails.game.domain.model.NumberSequence

interface MemoryGameRepository {
    suspend fun generateSequence(length: Int): NumberSequence
    suspend fun checkSequence(userInput: String, actualSequence: String): Boolean
    suspend fun getHighscore(): Int
    suspend fun incrementHighscore()
    suspend fun resetHighscore()
}