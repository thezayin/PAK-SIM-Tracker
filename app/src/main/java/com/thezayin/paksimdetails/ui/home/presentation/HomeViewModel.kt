package com.thezayin.paksimdetails.ui.home.presentation

import androidx.lifecycle.ViewModel
import com.thezayin.paksimdetails.framework.admob.domain.repository.InterstitialAdManager
import com.thezayin.paksimdetails.framework.admob.domain.repository.RewardedAdManager
import com.thezayin.paksimdetails.framework.analytics.analytics.Analytics
import com.thezayin.paksimdetails.framework.remote.RemoteConfig
import com.thezayin.paksimdetails.ui.home.presentation.event.ResultUiEvents
import com.thezayin.paksimdetails.ui.home.presentation.state.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    val interstitialAdManager: InterstitialAdManager,
    val rewardedAdManager: RewardedAdManager,
    val remoteConfig: RemoteConfig,
    val analytics: Analytics,


    ) :
    ViewModel() {


    private val _resultUiState = MutableStateFlow(ResultState.ResultUiState())
    val resultUiState = _resultUiState.asStateFlow()


    private fun resultUiEvent(event: ResultUiEvents) {
        when (event) {
            is ResultUiEvents.ErrorMessage -> {
                _resultUiState.update {
                    it.copy(
                        error = event.error
                    )
                }
            }

            ResultUiEvents.ShowErrorDialog -> {
                _resultUiState.update {
                    it.copy(
                        errorDialog = true
                    )
                }
            }

            ResultUiEvents.ShowLoading -> {
                _resultUiState.update {
                    it.copy(
                        loading = true
                    )
                }
            }

            ResultUiEvents.HideLoading -> {
                _resultUiState.update {
                    it.copy(
                        loading = false
                    )
                }
            }

            ResultUiEvents.HideErrorDialog -> {
                _resultUiState.update {
                    it.copy(
                        errorDialog = false
                    )
                }
            }
        }
    }

    fun hideErrorDialog() {
        resultUiEvent(ResultUiEvents.HideErrorDialog)
    }
}