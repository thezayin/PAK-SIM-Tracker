package com.thezayin.paksimdetails.ui.home.presentation.event

sealed interface ResultUiEvents {
    data object ShowLoading : ResultUiEvents
    data object HideLoading : ResultUiEvents
    data object ShowErrorDialog : ResultUiEvents
    data object HideErrorDialog : ResultUiEvents
    data class ErrorMessage(val error: String) : ResultUiEvents
}