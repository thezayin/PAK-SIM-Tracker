package com.thezayin.data.repository

import android.util.Log
import com.thezayin.data.network.ResultApi
import com.thezayin.domain.repository.ResultRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResultRepositoryImpl(private val api: ResultApi) : ResultRepository {
    override suspend fun searchNumber(string: String): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading)
            val response = api.searchNumber(string)
            emit(Response.Success(response))

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}