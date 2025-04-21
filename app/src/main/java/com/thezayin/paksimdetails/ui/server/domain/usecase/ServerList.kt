package com.thezayin.paksimdetails.ui.server.domain.usecase

import com.thezayin.paksimdetails.ui.server.domain.model.ServerModel
import com.thezayin.paksimdetails.ui.server.domain.repository.ServerRepository
import com.thezayin.paksimdetails.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ServerList : suspend () -> Flow<Response<List<ServerModel>>>

class ServerListImpl(private val repository: ServerRepository) : ServerList {
    override suspend fun invoke(): Flow<Response<List<ServerModel>>> {
        return repository.getServerList()
    }
}