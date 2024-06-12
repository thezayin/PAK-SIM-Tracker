package com.thezayin.paksimdetails.presentation.servers.data.repository

import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.paksimdetails.presentation.servers.domain.model.ServerList
import com.thezayin.paksimdetails.presentation.servers.domain.repository.ServerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerRepositoryImpl(
    private val remoteConfig: RemoteConfig
) : ServerRepository {
    override fun getServerList(): Flow<Response<List<ServerList>>> = flow {
        try {
            emit(Response.Loading)
            val response = listOf(
                ServerList(serverName = "Server 1", serverUrl = remoteConfig.getFirstUrl),
                ServerList(serverName = "Server 2", serverUrl = remoteConfig.getSecondUrl),
                ServerList(serverName = "Server 3", serverUrl = remoteConfig.getThirdUrl),
                ServerList(serverName = "Server 4", serverUrl = remoteConfig.getFourthUrl),
                ServerList(serverName = "Server 5", serverUrl = remoteConfig.getFifthUrl),
            )
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}