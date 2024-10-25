package com.thezayin.paksimdetails.game.domain.usecase

import com.thezayin.paksimdetails.game.domain.repository.MemoryGameRepository

interface GetHighscoreUseCase {
    suspend operator fun invoke(): Int
}
class GetHighscoreUseCaseImpl(
    private val repository: MemoryGameRepository
) : GetHighscoreUseCase {
    override suspend fun invoke(): Int {
        return repository.getHighscore()
    }
}