package com.thezayin.paksimdetails.domain.repository

import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow


interface SimRepository {
    suspend fun searchNumber(string: String): Flow<Response<String>>
}