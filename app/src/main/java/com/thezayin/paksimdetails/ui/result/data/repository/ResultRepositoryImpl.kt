package com.thezayin.paksimdetails.ui.result.data.repository

import com.thezayin.paksimdetails.framework.utils.Response
import com.thezayin.paksimdetails.ui.result.data.network.ResultApi
import com.thezayin.paksimdetails.ui.result.domain.repository.ResultRepository
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