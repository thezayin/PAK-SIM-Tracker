package com.thezayin.paksimdetails.data.remote.repository

import android.util.Log
import com.thezayin.framework.utils.Response
import com.thezayin.paksimdetails.data.remote.network.ApiService
import com.thezayin.paksimdetails.domain.repository.SimRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SimRepositoryImpl(
    private val apiService: ApiService
) : SimRepository {
    override suspend fun searchNumber(string: String): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading)
            val response = apiService.searchNumber(string)
            Log.d("SimRepositoryImpl", "searchNumber: $response")
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Exception error occurred"))
        } catch (e: IOException) {
            emit(Response.Error(e.localizedMessage ?: "An IOException error occurred"))
        } catch (e: NullPointerException) {
            emit(Response.Error(e.localizedMessage ?: "An NullPointer error occurred"))
        } catch (e: IndexOutOfBoundsException) {
            emit(Response.Error(e.localizedMessage ?: "An Index Out of Bound error occurred"))
        } catch (e: IllegalArgumentException) {
            emit(Response.Error(e.localizedMessage ?: "An Illegal Argument error occurred"))
        } catch (e: IllegalStateException) {
            emit(Response.Error(e.localizedMessage ?: "An Illegal State error occurred"))
        }
    }
}