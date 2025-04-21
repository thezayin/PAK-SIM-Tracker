package com.thezayin.paksimdetails.ui.server.data

import com.thezayin.paksimdetails.framework.remote.RemoteConfig
import com.thezayin.paksimdetails.framework.utils.Response
import com.thezayin.paksimdetails.ui.server.domain.model.ServerModel
import com.thezayin.paksimdetails.ui.server.domain.repository.ServerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerRepositoryImpl(private val remoteConfig: RemoteConfig) : ServerRepository {
    override fun getServerList(): Flow<Response<List<ServerModel>>> = flow {
        try {
            emit(Response.Loading)
            val list = listOf(
                ServerModel(1, "Server 1", remoteConfig.serverList.first),
                ServerModel(2, "Server 2", remoteConfig.serverList.second),
                ServerModel(3, "Server 3", remoteConfig.serverList.third),
                ServerModel(4, "Server 4", remoteConfig.serverList.fourth),
                ServerModel(5, "Server 5", remoteConfig.serverList.fifth)
            )
            emit(Response.Success(list))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}