package com.thezayin.domain.usecase

import com.thezayin.domain.repository.ResultRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetResult : suspend (String) -> Flow<Response<String>>

class GetResultImpl(private val repository: ResultRepository) : GetResult {
    override suspend fun invoke(number: String): Flow<Response<String>> =
        repository.searchNumber(number)
}