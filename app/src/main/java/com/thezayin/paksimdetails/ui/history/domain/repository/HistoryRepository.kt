package com.thezayin.paksimdetails.ui.history.domain.repository

import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel
import com.thezayin.paksimdetails.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryData(): Flow<Response<List<HistoryModel>?>>
    fun addData(historyModel: HistoryModel): Flow<Response<Long>>
    fun deleteAllData(): Flow<Response<Unit>>
}