package com.thezayin.paksimdetails.presentation.servers.domain.usecase

import com.thezayin.framework.utils.Response
import com.thezayin.paksimdetails.presentation.servers.domain.model.ServerList
import com.thezayin.paksimdetails.presentation.servers.domain.repository.ServerRepository
import kotlinx.coroutines.flow.Flow

interface ServerUseCase : suspend () -> Flow<Response<List<ServerList>>>

class ServerUseCaseImpl(
    private val repository: ServerRepository
) : ServerUseCase {
    override suspend fun invoke(): Flow<Response<List<ServerList>>> = repository.getServerList()
}