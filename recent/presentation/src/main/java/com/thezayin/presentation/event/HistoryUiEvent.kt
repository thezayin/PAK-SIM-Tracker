package com.thezayin.presentation.event

import com.thezayin.domain.model.HistoryModel

sealed interface HistoryUiEvent {
    data object ShowLoading : HistoryUiEvent
    data object HideLoading : HistoryUiEvent
    data object ShowErrorDialog : HistoryUiEvent
    data object HideErrorDialog : HistoryUiEvent
    data class ShowHistoryNotFound(val boolean: Boolean?) : HistoryUiEvent
    data class ErrorMessage(val error: String) : HistoryUiEvent
    data class HistorySuccess(val list: List<HistoryModel>?) : HistoryUiEvent
    data class DeleteDialogShow(val success: Boolean) : HistoryUiEvent
}