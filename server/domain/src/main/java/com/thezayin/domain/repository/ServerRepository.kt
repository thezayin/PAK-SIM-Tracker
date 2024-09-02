package com.thezayin.domain.repository

import com.thezayin.domain.model.ServerModel
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ServerRepository {
    fun getServerList(): Flow<Response<List<ServerModel>>>
}