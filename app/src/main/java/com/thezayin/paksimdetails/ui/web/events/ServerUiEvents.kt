package com.thezayin.paksimdetails.ui.web.events

sealed interface WebUiEvents {
    data object ShowLoading : WebUiEvents
    data object HideLoading : WebUiEvents
    data object ShowErrorDialog : WebUiEvents
    data object HideErrorDialog : WebUiEvents
    data class ErrorMessage(val error: String) : WebUiEvents
}