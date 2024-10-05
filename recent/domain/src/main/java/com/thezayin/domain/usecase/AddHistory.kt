package com.thezayin.domain.usecase

import com.thezayin.domain.model.HistoryModel
import com.thezayin.domain.repository.HistoryRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface AddHistory : suspend (HistoryModel) -> Flow<Response<Long>>

class AddHistoryImpl(private val repository: HistoryRepository) : AddHistory {
    override suspend fun invoke(history: HistoryModel): Flow<Response<Long>> =
        repository.addData(history)
}