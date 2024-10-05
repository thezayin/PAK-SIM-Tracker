package com.thezayin.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.domain.model.HistoryModel
import com.thezayin.domain.model.ResultModel
import com.thezayin.domain.usecase.AddHistory
import com.thezayin.domain.usecase.GetResult
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.presentation.event.ResultUiEvents
import com.thezayin.presentation.state.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.Date

class HomeViewModel(
    val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig,
    val analytics: Analytics,
    private val getResult: GetResult,
    private val addHistory: AddHistory
) :
    ViewModel() {

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("dd/M/yyyy")
    private val currentDate: String = sdf.format(Date())

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    private val _resultUiState = MutableStateFlow(ResultState.ResultUiState())
    val resultUiState = _resultUiState.asStateFlow()

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd().apply {
        } ?: run {
            delay(10000)
            googleManager.createNativeAd()
        }
    }

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

            is ResultUiEvents.ResultSuccess -> {
                _resultUiState.update {
                    it.copy(
                        result = event.result
                    )
                }
            }

            is ResultUiEvents.ShowResultNotFound -> {
                _resultUiState.update {
                    it.copy(
                        resultNotFound = event.boolean
                    )
                }
            }
        }
    }

    private fun addToHistory(
        number: String,
        searchSuccess: Boolean,
        name: String?,
        cnic: String?,
        address: String?
    ) = viewModelScope.launch {
        addHistory(
            HistoryModel(
                searchSuccess = searchSuccess,
                phoneNumber = number,
                date = currentDate,
                name = name,
                cnic = cnic,
                address = address
            )
        ).collect {
            Log.d("ResultViewModel", "addToHistory: $it")
        }
    }

    fun searchNumber(phoneNumber: String) = viewModelScope.launch {
        getResult(phoneNumber)
            .catch { e ->
                // Handle the exception here
                errorMessages(e.localizedMessage ?: "An error occurred")
                showErrorDialog()
                setResultNull()
                resultNotFound(true)
                hideLoading()
            }
            .collect { response ->
                when (response) {
                    is Response.Success -> {
                        setResultNull()
                        setResultNotFound()
                        delay(2000L)
                        val doc: Document = Jsoup.parse(response.data)
                        val notFoundMessage = doc.select("h4").firstOrNull()?.text()
                        if (notFoundMessage?.contains("Records Not Found") == true) {
                            // If the message is found, update the state to show the "no result" UI
                            addToHistory(phoneNumber, false, null, null, null)
                            resultNotFound(false)
                            hideLoading()
                            return@collect
                        }
                        val table = doc.select("table")
                        val rows = table.select("tr")
                        val results = mutableListOf<ResultModel>()
                        for (row in rows) {
                            val cols = row.select("td")
                            for (col in cols) {
                                val result = ResultModel(
                                    number = cols[0].text(),
                                    name = cols[1].text(),
                                    cnic = cols[2].text(),
                                    address = cols[3].text()
                                )
                                results.add(result)
                                resultSuccess(result)
                            }
                        }
                        if (results.isNotEmpty()) {
                            // Assuming you want to add a history entry per phone number search
                            // You can aggregate results or pick the first one
                            val firstResult = results.first()
                            addToHistory(
                                number = phoneNumber,
                                searchSuccess = true,
                                name = firstResult.name,
                                cnic = firstResult.cnic,
                                address = firstResult.address
                            )
                        }

                        hideLoading()
                    }

                    is Response.Error -> {
                        errorMessages(response.e)
                        showErrorDialog()
                        hideLoading()
                    }

                    is Response.Loading -> {
                        showLoading()
                    }
                }
            }
    }

    private fun resultSuccess(resultModel: ResultModel?) {
        resultUiEvent(ResultUiEvents.ResultSuccess(resultModel))
    }

    private fun errorMessages(error: String) {
        resultUiEvent(ResultUiEvents.ErrorMessage(error))
    }

    private fun showErrorDialog() {
        resultUiEvent(ResultUiEvents.ShowErrorDialog)
    }

    fun hideErrorDialog() {
        resultUiEvent(ResultUiEvents.HideErrorDialog)
    }

    private fun hideLoading() {
        resultUiEvent(ResultUiEvents.HideLoading)
    }

    private fun showLoading() {
        resultUiEvent(ResultUiEvents.ShowLoading)
    }

    private fun resultNotFound(boolean: Boolean) {
        resultUiEvent(ResultUiEvents.ShowResultNotFound(boolean))
    }

    private fun setResultNull() {
        resultUiEvent(ResultUiEvents.ResultSuccess(null))
    }

    private fun setResultNotFound() {
        resultUiEvent(ResultUiEvents.ShowResultNotFound(null))
    }
}