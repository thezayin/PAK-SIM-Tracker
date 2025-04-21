package com.thezayin.paksimdetails.ui.history.domain.usecase

import com.thezayin.paksimdetails.ui.history.domain.repository.HistoryRepository
import com.thezayin.paksimdetails.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ClearHistoryUseCase : suspend () -> Flow<Response<Unit>>

class ClearHistoryUseCaseImpl(private val repository: HistoryRepository) : ClearHistoryUseCase {
    override suspend fun invoke(): Flow<Response<Unit>> = repository.deleteAllData()
}