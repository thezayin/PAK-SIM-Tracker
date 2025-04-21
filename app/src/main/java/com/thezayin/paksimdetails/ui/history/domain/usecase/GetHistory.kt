package com.thezayin.paksimdetails.ui.history.domain.usecase

import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel
import com.thezayin.paksimdetails.ui.history.domain.repository.HistoryRepository
import com.thezayin.paksimdetails.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetHistory : suspend () -> Flow<Response<List<HistoryModel>?>>

class GetHistoryImpl(private val historyRepository: HistoryRepository) : GetHistory {
    override suspend fun invoke(): Flow<Response<List<HistoryModel>?>> =
        historyRepository.getHistoryData()
}