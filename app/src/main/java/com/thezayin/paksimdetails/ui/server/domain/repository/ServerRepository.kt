package com.thezayin.paksimdetails.ui.server.domain.repository

import com.thezayin.paksimdetails.ui.server.domain.model.ServerModel
import com.thezayin.paksimdetails.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ServerRepository {
    fun getServerList(): Flow<Response<List<ServerModel>>>
}