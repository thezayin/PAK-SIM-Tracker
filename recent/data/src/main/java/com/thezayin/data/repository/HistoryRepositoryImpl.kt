package com.thezayin.data.repository

import android.util.Log
import com.thezayin.data.dao.HistoryDao
import com.thezayin.domain.model.HistoryModel
import com.thezayin.domain.repository.HistoryRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(private val dao: HistoryDao) : HistoryRepository {
    override fun getHistoryData(): Flow<Response<List<HistoryModel>?>> = flow {
        try {
            emit(Response.Loading)
            Log.d("jejeRepo", "getHistoryData: ")
            val list = dao.getAllHistory()
            Log.d("jejeRepoRE", "getHistoryData: ${list.size}")
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
            Log.d("jejeRepo", "deleteAllData: ")
            val result = dao.clearHistory()
            Log.d("jejeRepoRE", "deleteAllData:${result} ")
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}