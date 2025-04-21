package com.thezayin.paksimdetails.ui.result.presentation.event

import com.thezayin.paksimdetails.ui.result.domain.model.ResultModel

sealed interface ResultUiEvents {
    data object ShowLoading : ResultUiEvents
    data object HideLoading : ResultUiEvents
    data object ShowErrorDialog : ResultUiEvents
    data object HideErrorDialog : ResultUiEvents
    data class ShowResultNotFound(val boolean: Boolean?) : ResultUiEvents
    data class ErrorMessage(val error: String) : ResultUiEvents
    data class ResultSuccess(val result: ResultModel?) : ResultUiEvents
}