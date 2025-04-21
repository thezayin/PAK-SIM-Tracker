package com.thezayin.paksimdetails.ui.history.domain.usecase

import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel
import com.thezayin.paksimdetails.ui.history.domain.repository.HistoryRepository
import com.thezayin.paksimdetails.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface AddHistory : suspend (HistoryModel) -> Flow<Response<Long>>

class AddHistoryImpl(private val repository: HistoryRepository) : AddHistory {
    override suspend fun invoke(history: HistoryModel): Flow<Response<Long>> =
        repository.addData(history)
}