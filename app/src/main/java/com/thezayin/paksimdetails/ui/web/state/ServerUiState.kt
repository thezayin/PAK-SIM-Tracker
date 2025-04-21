package com.thezayin.paksimdetails.ui.web.state

import com.thezayin.paksimdetails.framework.state.State

sealed interface WebState : State {
    data class WebUiState(
        val loading: Boolean = false,
        val errorDialog: Boolean = false,
        val error: String = "",
    )
}