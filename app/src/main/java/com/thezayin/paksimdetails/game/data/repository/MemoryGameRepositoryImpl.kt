package com.thezayin.paksimdetails.game.data.repository

import com.thezayin.paksimdetails.game.data.local.MemoryGameLocalDataSource
import com.thezayin.paksimdetails.game.domain.model.NumberSequence
import com.thezayin.paksimdetails.game.domain.repository.MemoryGameRepository
import kotlin.random.Random

class MemoryGameRepositoryImpl(
    private val localDataSource: MemoryGameLocalDataSource
) : MemoryGameRepository {

    override suspend fun generateSequence(length: Int): NumberSequence {
        val sequence = (1..length)
            .map { Random.nextInt(0, 10) }
            .joinToString("")
        return NumberSequence(sequence)
    }

    override suspend fun checkSequence(userInput: String, actualSequence: String): Boolean {
        return userInput == actualSequence
    }

    override suspend fun getHighscore(): Int {
        return localDataSource.getHighscore()
    }

    override suspend fun incrementHighscore() {
        val currentHighscore = getHighscore()
        localDataSource.saveHighscore(currentHighscore + 1)
    }

    override suspend fun resetHighscore() {
        localDataSource.saveHighscore(0)
    }
}