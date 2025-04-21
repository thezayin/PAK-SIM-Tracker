package com.thezayin.paksimdetails.ui.history.data.repository

import com.thezayin.paksimdetails.framework.utils.Response
import com.thezayin.paksimdetails.ui.history.data.dao.HistoryDao
import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel
import com.thezayin.paksimdetails.ui.history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(private val dao: HistoryDao) : HistoryRepository {
    override fun getHistoryData(): Flow<Response<List<HistoryModel>?>> = flow {
        try {
            emit(Response.Loading)
            val list = dao.getAllHistory()
            emit(Response.Success(list))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun addData(historyModel: HistoryModel): Flow<Response<Long>> = flow {
        try {
            emit(Response.Loading)
            val id = dao.insertHistory(historyModel)
            emit(Response.Success(id))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun deleteAllData(): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            dao.clearHistory()
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}