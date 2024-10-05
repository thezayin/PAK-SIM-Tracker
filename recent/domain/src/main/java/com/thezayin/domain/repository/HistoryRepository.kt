package com.thezayin.domain.repository

import com.thezayin.domain.model.HistoryModel
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistoryData(): Flow<Response<List<HistoryModel>?>>
    fun addData(historyModel: HistoryModel): Flow<Response<Long>>
    fun deleteAllData(): Flow<Response<Unit>>
}