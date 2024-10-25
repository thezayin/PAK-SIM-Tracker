package com.thezayin.paksimdetails.game.domain.usecase

import com.thezayin.paksimdetails.game.domain.repository.MemoryGameRepository

interface CheckSequenceUseCase {
    suspend operator fun invoke(userInput: String, actualSequence: String): Boolean
}
class CheckSequenceUseCaseImpl(
    private val repository: MemoryGameRepository
) : CheckSequenceUseCase {
    override suspend fun invoke(userInput: String, actualSequence: String): Boolean {
        val isCorrect = repository.checkSequence(userInput, actualSequence)
        if (isCorrect) {
            repository.incrementHighscore()
        }
        return isCorrect
    }
}