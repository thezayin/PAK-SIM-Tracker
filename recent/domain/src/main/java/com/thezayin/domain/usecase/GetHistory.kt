package com.thezayin.domain.usecase

import com.thezayin.domain.model.HistoryModel
import com.thezayin.domain.repository.HistoryRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetHistory : suspend () -> Flow<Response<List<HistoryModel>?>>

class GetHistoryImpl(private val historyRepository: HistoryRepository) : GetHistory {
    override suspend fun invoke(): Flow<Response<List<HistoryModel>?>> =
        historyRepository.getHistoryData()
}