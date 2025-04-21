package com.thezayin.paksimdetails.ui.result.domain.repository

import com.thezayin.paksimdetails.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ResultRepository {
    suspend fun searchNumber(string: String): Flow<Response<String>>
}