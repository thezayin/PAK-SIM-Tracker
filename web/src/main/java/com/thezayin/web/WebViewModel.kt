package com.thezayin.web

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.web.events.WebUiEvents
import com.thezayin.web.state.WebState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WebViewModel(
    val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig,
    val analytics: Analytics
) : ViewModel() {
    private val _webUiState = MutableStateFlow(WebState.WebUiState())
    val webUiState = _webUiState.asStateFlow()

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    fun getNativeAd() = viewModelScope.launch {
//        nativeAd.value = googleManager.createNativeAd().apply {
//        } ?: run {
//            delay(10000)
//            googleManager.createNativeAd()
//        }
    }

    init {
        showLoading()
    }

    fun showLoading() {
        webEvents(WebUiEvents.ShowLoading)
    }

    fun hideLoading() {
        webEvents(WebUiEvents.HideLoading)
    }

    fun showErrorDialog() {
        webEvents(WebUiEvents.ShowErrorDialog)
    }

    fun hideErrorDialog() {
        webEvents(WebUiEvents.HideErrorDialog)
    }

    fun errorMessage(error: String) {
        webEvents(WebUiEvents.ErrorMessage(error))
    }

    private fun webEvents(events: WebUiEvents) {
        when (events) {
            is WebUiEvents.ShowLoading -> {
                _webUiState.update {
                    it.copy(loading = true)
                }
            }

            is WebUiEvents.HideLoading -> {
                _webUiState.update {
                    it.copy(loading = false)
                }
            }

            is WebUiEvents.ShowErrorDialog -> {
                _webUiState.update {
                    it.copy(errorDialog = true)
                }
            }

            is WebUiEvents.HideErrorDialog -> {
                _webUiState.update {
                    it.copy(errorDialog = false)
                }
            }

            is WebUiEvents.ErrorMessage -> {
                _webUiState.update {
                    it.copy(error = events.error)
                }
            }
        }
    }
}