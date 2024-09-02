package com.thezayin.presentation.events

import com.thezayin.domain.model.ServerModel


sealed interface ServerUiEvents {
    data object ShowLoading : ServerUiEvents
    data object HideLoading : ServerUiEvents
    data object ShowErrorDialog : ServerUiEvents
    data object HideErrorDialog : ServerUiEvents
    data class ErrorMessage(val error: String) : ServerUiEvents
    data class ServerList(val list: List<ServerModel>) : ServerUiEvents
}