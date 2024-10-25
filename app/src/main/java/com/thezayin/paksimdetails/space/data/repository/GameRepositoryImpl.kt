package com.thezayin.paksimdetails.space.data.repository

import android.content.Context
import com.thezayin.framework.utils.ConnectivityHelper
import com.thezayin.paksimdetails.space.data.local.GameLocalDataSource
import com.thezayin.paksimdetails.space.data.remote.GameRemoteDataSource
import com.thezayin.paksimdetails.space.domain.model.GameScore
import com.thezayin.paksimdetails.space.domain.repository.GameRepository
import java.util.UUID

class GameRepositoryImpl(
    private val context: Context,
    private val localDataSource: GameLocalDataSource,
    private val remoteDataSource: GameRemoteDataSource
) : GameRepository {

    private val uniqueId: String = getUniqueId()

    override suspend fun getHighScore(): GameScore {
        val localScore = localDataSource.getHighScore() ?: 0
        val remoteScore = remoteDataSource.fetchHighScore(uniqueId)
        val highScore = maxOf(localScore, remoteScore)
        return GameScore(uniqueId, highScore)
    }

    override suspend fun saveHighScore(score: GameScore) {
        localDataSource.saveHighScore(score.score)
        if (isConnectedToInternet()) {
            remoteDataSource.uploadHighScore(score.userId, score.score)
        } else {
            localDataSource.savePendingScore(score.score)
        }
    }

    private fun getUniqueId(): String {
        return localDataSource.getUniqueId() ?: UUID.randomUUID().toString().also {
            localDataSource.saveUniqueId(it)
        }
    }

    private fun isConnectedToInternet(): Boolean {
        return ConnectivityHelper.isConnected(context)
    }
}