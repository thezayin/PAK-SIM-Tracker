package com.thezayin.paksimdetails.space.domain.repository

import com.thezayin.paksimdetails.space.domain.model.GameScore


interface GameRepository {
    suspend fun getHighScore(): GameScore
    suspend fun saveHighScore(score: GameScore)
}