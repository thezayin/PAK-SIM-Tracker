package com.thezayin.domain.repository

import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ResultRepository {
    suspend fun searchNumber(string: String): Flow<Response<String>>
}