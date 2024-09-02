package com.thezayin.presentation.event

import com.thezayin.domain.model.ResultModel

sealed interface ResultUiEvents {

    data object ShowLoading : ResultUiEvents
    data object HideLoading : ResultUiEvents
    data object ShowErrorDialog : ResultUiEvents
    data object HideErrorDialog : ResultUiEvents
    data class ShowResultNotFound(val boolean: Boolean?) : ResultUiEvents
    data class ErrorMessage(val error: String) : ResultUiEvents
    data class ResultSuccess(val result: ResultModel?) : ResultUiEvents
}