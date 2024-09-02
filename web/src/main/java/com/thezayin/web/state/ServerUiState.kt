package com.thezayin.web.state

import com.thezayin.framework.state.State

sealed interface WebState : State {
    data class WebUiState(
        val loading: Boolean = false,
        val errorDialog: Boolean = false,
        val error: String = "",
    )
}