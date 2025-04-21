package com.thezayin.paksimdetails.ui.result.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.paksimdetails.framework.analytics.analytics.Analytics
import com.thezayin.paksimdetails.framework.remote.RemoteConfig
import com.thezayin.paksimdetails.framework.utils.Response
import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel
import com.thezayin.paksimdetails.ui.history.domain.usecase.AddHistory
import com.thezayin.paksimdetails.ui.result.domain.model.ResultModel
import com.thezayin.paksimdetails.ui.result.domain.usecase.GetResult
import com.thezayin.paksimdetails.ui.result.presentation.event.ResultUiEvents
import com.thezayin.paksimdetails.ui.result.presentation.state.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

class ResultViewModel(
    private val getResult: GetResult,
    private val addHistory: AddHistory,
    val remoteConfig: RemoteConfig,
    val analytics: Analytics
) : ViewModel() {

    private val _resultUiState = MutableStateFlow(ResultState.ResultUiState())
    val resultUiState = _resultUiState.asStateFlow()

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("dd/M/yyyy")
    private val currentDate: String = sdf.format(Date())


    private fun resultUiEvent(event: ResultUiEvents) {
        when (event) {
            is ResultUiEvents.ShowResultNotFound -> _resultUiState.update { it.copy(resultNotFound = event.boolean) }
            is ResultUiEvents.ErrorMessage -> _resultUiState.update { it.copy(error = event.error) }
            ResultUiEvents.ShowErrorDialog -> _resultUiState.update { it.copy(errorDialog = true) }
            ResultUiEvents.ShowLoading -> _resultUiState.update { it.copy(loading = true) }
            ResultUiEvents.HideLoading -> _resultUiState.update { it.copy(loading = false) }
            ResultUiEvents.HideErrorDialog -> _resultUiState.update { it.copy(errorDialog = false) }
            is ResultUiEvents.ResultSuccess -> _resultUiState.update { it.copy(result = event.result) }
        }
    }

    private fun addToHistory(
        number: String, searchSuccess: Boolean, name: String?, cnic: String?, address: String?
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
            Timber.tag("History").d("History added successfully: $it")
        }
    }

    fun searchNumber(phoneNumber: String) = viewModelScope.launch {
        getResult(phoneNumber).catch { e ->
            // Handle the exception here
            errorMessages(e.localizedMessage ?: "An error occurred")
            showErrorDialog()
            setResultNull()
            resultNotFound(true)
            hideLoading()
        }.collect { response ->
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

    private fun resultSuccess(resultModel: ResultModel) {
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

    private fun setResultNotFound() {
        resultUiEvent(ResultUiEvents.ShowResultNotFound(null))
    }

    private fun setResultNull() {
        resultUiEvent(ResultUiEvents.ResultSuccess(null))
    }

    private fun resultNotFound(boolean: Boolean) {
        resultUiEvent(ResultUiEvents.ShowResultNotFound(boolean))
    }
}