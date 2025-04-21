package com.thezayin.paksimdetails.ui.result.domain.usecase

import com.thezayin.paksimdetails.framework.utils.Response
import com.thezayin.paksimdetails.ui.result.domain.repository.ResultRepository
import kotlinx.coroutines.flow.Flow

interface GetResult : suspend (String) -> Flow<Response<String>>

class GetResultImpl(private val repository: ResultRepository) : GetResult {
    override suspend fun invoke(number: String): Flow<Response<String>> =
        repository.searchNumber(number)
}