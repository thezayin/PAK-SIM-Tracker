package com.thezayin.paksimdetails.space.domain.usecase

import com.thezayin.paksimdetails.space.domain.model.GameScore
import com.thezayin.paksimdetails.space.domain.repository.GameRepository

interface SaveHighScoreUseCase {
    suspend fun execute(score: GameScore)
}

class SaveHighScoreUseCaseImpl(
    private val gameRepository: GameRepository
) : SaveHighScoreUseCase {

    override suspend fun execute(score: GameScore) {
        gameRepository.saveHighScore(score)
    }
}