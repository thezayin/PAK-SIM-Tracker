package com.thezayin.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.domain.model.ServerModel
import com.thezayin.domain.usecase.ServerList
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.events.ServerUiEvents
import com.thezayin.presentation.state.ServerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServerViewModel(
    val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig,
    val analytics: Analytics,
    private val serverList: ServerList
) : ViewModel() {
    private val _serverUiState = MutableStateFlow(ServerState.ServerUiState())
    val serverUiState = _serverUiState.asStateFlow()


    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd().apply {
        } ?: run {
            delay(10000)
            googleManager.createNativeAd()
        }
    }

    init {
        getServerList()
    }



    private fun getServerList() = viewModelScope.launch {
        serverList().collect {
            when (it) {
                is Response.Success -> {
                    delay(4000L)
                    hideLoading()
                    serverState(it.data)
                }

                is Response.Error -> {
                    hideLoading()
                    showErrorDialog()
                    errorMessage(it.e)
                }

                is Response.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun hideLoading() {
        serverUiEvents(ServerUiEvents.HideLoading)
    }

    private fun showLoading() {
        serverUiEvents(ServerUiEvents.ShowLoading)
    }

    private fun errorMessage(error: String) {
        serverUiEvents(ServerUiEvents.ErrorMessage(error))
    }

    private fun showErrorDialog() {
        serverUiEvents(ServerUiEvents.ShowErrorDialog)
    }

    fun hideErrorDialog() {
        serverUiEvents(ServerUiEvents.HideErrorDialog)
    }

    private fun serverState(list: List<ServerModel>) {
        serverUiEvents(ServerUiEvents.ServerList(list))
    }

    private fun serverUiEvents(events: ServerUiEvents) {
        when (events) {
            is ServerUiEvents.ShowLoading -> {
                _serverUiState.update {
                    it.copy(loading = true)
                }
            }

            is ServerUiEvents.HideLoading -> {
                _serverUiState.update {
                    it.copy(loading = false)
                }
            }

            is ServerUiEvents.ShowErrorDialog -> {
                _serverUiState.update {
                    it.copy(errorDialog = true)
                }
            }

            is ServerUiEvents.HideErrorDialog -> {
                _serverUiState.update {
                    it.copy(errorDialog = false)
                }
            }

            is ServerUiEvents.ErrorMessage -> {
                _serverUiState.update {
                    it.copy(error = events.error)
                }
            }

            is ServerUiEvents.ServerList -> {
                _serverUiState.update {
                    it.copy(list = events.list)
                }
            }
        }
    }
}