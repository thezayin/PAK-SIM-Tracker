package com.thezayin.paksimdetails.ui.server.presentation.events

import com.thezayin.paksimdetails.ui.server.domain.model.ServerModel


sealed interface ServerUiEvents {
    data object ShowLoading : ServerUiEvents
    data object HideLoading : ServerUiEvents
    data object ShowErrorDialog : ServerUiEvents
    data object HideErrorDialog : ServerUiEvents
    data class ErrorMessage(val error: String) : ServerUiEvents
    data class ServersList(val list: List<ServerModel>) : ServerUiEvents
}