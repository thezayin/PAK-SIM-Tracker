package com.thezayin.presentation.state

import com.thezayin.domain.model.ServerModel
import com.thezayin.domain.usecase.ServerList
import com.thezayin.framework.state.State

sealed interface ServerState : State {
    data class ServerUiState(
        val loading: Boolean = false,
        val list: List<ServerModel>? = null,
        val errorDialog: Boolean = false,
        val error: String = "",
    )
}