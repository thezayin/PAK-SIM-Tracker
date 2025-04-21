package com.thezayin.paksimdetails.ui.server.presentation.state

import com.thezayin.paksimdetails.framework.state.State
import com.thezayin.paksimdetails.ui.server.domain.model.ServerModel

sealed interface ServerState : State {
    data class ServerUiState(
        val loading: Boolean = false,
        val list: List<ServerModel>? = null,
        val errorDialog: Boolean = false,
        val error: String = "",
    )
}