package com.thezayin.paksimdetails.ui.home.presentation.state

import com.thezayin.paksimdetails.framework.state.State

sealed interface ResultState : State {
    data class ResultUiState(
        val loading: Boolean = false,
        val errorDialog: Boolean = false,
        val error: String = "",
    )
}