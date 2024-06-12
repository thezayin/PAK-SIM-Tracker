package com.thezayin.paksimdetails.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.helpers.AnalyticsHelper
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.paksimdetails.domain.model.SimDataModel
import com.thezayin.paksimdetails.domain.usecase.RemoteUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HomeViewModel(
    private val useCase: RemoteUseCase,
    val googleManager: GoogleManager,
    val analyticsHelper: AnalyticsHelper,
    val remoteConfig: RemoteConfig
) : ViewModel() {

    private val _simDetails = MutableStateFlow(GetSimDataState())
    val simDetails = _simDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(GetLoadingState())
    val isLoading = _isLoading.asStateFlow()

    private val _resultSuccess = MutableStateFlow(GetResultState())
    val resultSuccess = _resultSuccess.asStateFlow()

    private val _error = MutableStateFlow(GetErrorState())
    val error = _error.asStateFlow()

    fun searchNumber(string: String) {
        viewModelScope.launch {
            useCase(string).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _simDetails.update {
                            it.copy(
                                data = mutableStateOf(SimDataModel())
                            )
                        }
                        _resultSuccess.update {
                            it.copy(
                                isSuccessful = mutableStateOf(false)
                            )
                        }
                        _isLoading.update {
                            it.copy(
                                isLoading = mutableStateOf(true)
                            )
                        }
                        _error.update {
                            it.copy(
                                isError = mutableStateOf(false)
                            )
                        }
                    }

                    is Response.Success -> {
                        delay(3000L)
                        _error.update {
                            it.copy(
                                isError = mutableStateOf(false)
                            )
                        }
                        _resultSuccess.update {
                            it.copy(
                                isSuccessful = mutableStateOf(true)
                            )
                        }
                        _isLoading.update {
                            it.copy(
                                isLoading = mutableStateOf(false)
                            )
                        }
                        val doc: Document = Jsoup.parse(response.data)
                        val table = doc.select("table")
                        val rows = table.select("tr")
                        for (row in rows) {
                            val cols = row.select("td")
                            for (col in cols) {
                                _simDetails.update {
                                    it.copy(
                                        data = mutableStateOf(
                                            SimDataModel(
                                                number = cols[0].text(),
                                                name = cols[1].text(),
                                                cnic = cols[2].text(),
                                                address = cols[3].text()
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }

                    is Response.Error -> {
                        _simDetails.update {
                            it.copy(
                                data = mutableStateOf(SimDataModel())
                            )
                        }
                        _error.update {
                            it.copy(
                                isError = mutableStateOf(true)
                            )
                        }
                        _resultSuccess.update {
                            it.copy(
                                isSuccessful = mutableStateOf(false)
                            )
                        }
                        _isLoading.update {
                            it.copy(
                                isLoading = mutableStateOf(false)
                            )
                        }
                    }
                }
            }
        }
    }

    data class GetSimDataState(
        val data: MutableState<SimDataModel> = mutableStateOf(SimDataModel())
    )

    data class GetResultState(
        val isSuccessful: MutableState<Boolean> = mutableStateOf(false)
    )

    data class GetLoadingState(
        val isLoading: MutableState<Boolean> = mutableStateOf(false)
    )

    data class GetErrorState(
        val isError: MutableState<Boolean> = mutableStateOf(false)
    )
}