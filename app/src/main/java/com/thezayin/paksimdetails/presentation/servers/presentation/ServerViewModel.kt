package com.thezayin.paksimdetails.presentation.servers.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.framework.utils.Response
import com.thezayin.paksimdetails.presentation.servers.domain.model.ServerList
import com.thezayin.paksimdetails.presentation.servers.domain.usecase.ServerUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class   ServerViewModel(
    private val serverUseCase: ServerUseCase
) : ViewModel() {

    private val _serverList = MutableStateFlow(ServerState())
    val serverList = _serverList.asStateFlow()

    private val _isLoading = MutableStateFlow(GetLoadingState())
    val isLoading = _isLoading.asStateFlow()

    private val _loading = MutableStateFlow(LoadingState())
    val loading = _loading.asStateFlow()

    private val _isBlocked = MutableStateFlow(IsBlockedState())
    val isBlocked = _isBlocked.asStateFlow()

    init {
        getServerList()
    }

    fun webPageLoading(
        boolean: Boolean
    ) = viewModelScope.launch {
        _loading.update {
            it.copy(
                isLoading = mutableStateOf(boolean)
            )
        }
    }

    fun isBlocked(
    ) = viewModelScope.launch {
        _isBlocked.update {
            it.copy(
                isBlocked = mutableStateOf(true)
            )
        }
    }

    private fun getServerList() = viewModelScope.launch {
        serverUseCase().collect { response ->
            when (response) {
                is Response.Loading -> {
                    _isLoading.update {
                        it.copy(
                            isLoading = mutableStateOf(true)
                        )
                    }
                }

                is Response.Success -> {
                    delay(1000L)
                    _isLoading.update {
                        it.copy(
                            isLoading = mutableStateOf(false)
                        )
                    }

                    _serverList.update {
                        it.copy(
                            list = mutableStateOf(response.data)
                        )
                    }
                }

                is Response.Error -> {
                    _isLoading.update {
                        it.copy(
                            isLoading = mutableStateOf(false)
                        )
                    }
                }
            }
        }
    }

    data class ServerState(
        val list: MutableState<List<ServerList>> = mutableStateOf(emptyList())
    )

    data class GetLoadingState(
        val isLoading: MutableState<Boolean> = mutableStateOf(false)
    )

    data class LoadingState(
        val isLoading: MutableState<Boolean> = mutableStateOf(true)
    )

    data class IsBlockedState(
        val isBlocked: MutableState<Boolean> = mutableStateOf(false)
    )
}