package com.thezayin.domain.usecase

import com.thezayin.domain.model.ServerModel
import com.thezayin.domain.repository.ServerRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ServerList : suspend () -> Flow<Response<List<ServerModel>>>

class ServerListImpl(private val repository: ServerRepository) : ServerList {
    override suspend fun invoke(): Flow<Response<List<ServerModel>>> {
        return repository.getServerList()
    }
}