package com.thezayin.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.domain.usecase.ClearHistoryUseCase
import com.thezayin.domain.usecase.GetHistory
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.event.HistoryUiEvent
import com.thezayin.presentation.state.HistoryUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    val clearHistoryUseCase: ClearHistoryUseCase,
    val getHistory: GetHistory,
    val remoteConfig: RemoteConfig,
    val googleManager: GoogleManager,
    val analytics: Analytics
) : ViewModel() {
    private val _historyUiState = MutableStateFlow(HistoryUiState())
    val historyUiState = _historyUiState.asStateFlow()

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

//    fun getNativeAd() = viewModelScope.launch {
//        nativeAd.value = googleManager.createNativeAd().apply {
//        } ?: run {
//            delay(10000)
//            googleManager.createNativeAd()
//        }
//    }

    private fun historyUiEvent(event: HistoryUiEvent) {
        when (event) {
            is HistoryUiEvent.HistorySuccess -> _historyUiState.update { it.copy(list = event.list) }
            is HistoryUiEvent.ErrorMessage -> _historyUiState.update { it.copy(error = event.error) }
            HistoryUiEvent.HideErrorDialog -> _historyUiState.update { it.copy(errorDialog = false) }
            HistoryUiEvent.HideLoading -> _historyUiState.update { it.copy(loading = false) }
            HistoryUiEvent.ShowErrorDialog -> _historyUiState.update { it.copy(errorDialog = true) }
            is HistoryUiEvent.ShowHistoryNotFound -> _historyUiState.update { it.copy(resultNotFound = event.boolean) }
            HistoryUiEvent.ShowLoading -> _historyUiState.update { it.copy(loading = true) }
            is HistoryUiEvent.DeleteDialogShow -> _historyUiState.update { it.copy(deleteDialogShow = true) }
        }
    }

    init {
        getSearchHistory()
    }

    fun getSearchHistory() = viewModelScope.launch {
        getHistory().collect { response ->
            when (response) {
                is Response.Success -> {
                    delay(5000L)
                    hideErrorDialog()
                    hideLoading()
                    if (response.data.isNullOrEmpty()) {
                        setResultNotFound()
                    } else {
                        resultNotFound(false)
                        historyUiEvent(HistoryUiEvent.HistorySuccess(response.data))
                    }
                }

                is Response.Error -> {
                    hideLoading()
                    showErrorDialog()
                    errorMessages(response.e)
                }

                is Response.Loading -> {
                    showLoading()
                }
            }
        }
    }

    fun deleteSearchHistory() = viewModelScope.launch {

        Log.d("jejeFunEnter", "deleteSearchHistory")
        clearHistoryUseCase().collect { response ->
            when (response) {
                is Response.Success -> {
                    Log.d("jejeFunEnter", "deleteSearchHistorySuccess")
                    delay(5000L)
                    hideErrorDialog()
                    showDeleteDialog(true)
                    getSearchHistory()
                }

                is Response.Error -> {
                    Log.d("jejeFunEnter", "deleteSearchHistoryError")
                    showErrorDialog()
                    showDeleteDialog(false)
                    errorMessages(response.e)
                }

                is Response.Loading -> {
                    Log.d("jejeFunEnter", "deleteSearchHistoryLoading")
                    showDeleteDialog(false)
                    hideErrorDialog()
                    showLoading()
                }
            }
        }
    }


    fun showDeleteDialog(show: Boolean) {
        historyUiEvent(HistoryUiEvent.DeleteDialogShow(show))
    }

    private fun errorMessages(error: String) {
        historyUiEvent(HistoryUiEvent.ErrorMessage(error))
    }

    private fun showErrorDialog() {
        historyUiEvent(HistoryUiEvent.ShowErrorDialog)
    }

    fun hideErrorDialog() {
        historyUiEvent(HistoryUiEvent.HideErrorDialog)
    }

    private fun hideLoading() {
        historyUiEvent(HistoryUiEvent.HideLoading)
    }

    private fun showLoading() {
        historyUiEvent(HistoryUiEvent.ShowLoading)
    }

    private fun setResultNotFound() {
        historyUiEvent(HistoryUiEvent.ShowHistoryNotFound(null))
    }

    private fun resultNotFound(boolean: Boolean) {
        historyUiEvent(HistoryUiEvent.ShowHistoryNotFound(boolean))
    }
}