package com.thezayin.domain.usecase

import com.thezayin.domain.repository.HistoryRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ClearHistoryUseCase : suspend () -> Flow<Response<Unit>>

class ClearHistoryUseCaseImpl(private val repository: HistoryRepository) : ClearHistoryUseCase {
    override suspend fun invoke(): Flow<Response<Unit>> = repository.deleteAllData()
}