package com.thezayin.paksimdetails.space.domain.usecase

import com.thezayin.paksimdetails.space.domain.repository.GameRepository

interface GetHighScoreUseCase {
    suspend fun execute(): Int
}
class GetHighScoreUseCaseImpl(
    private val gameRepository: GameRepository
) : GetHighScoreUseCase {

    override suspend fun execute(): Int {
        return gameRepository.getHighScore().score
    }
}