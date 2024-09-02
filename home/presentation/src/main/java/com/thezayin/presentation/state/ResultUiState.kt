package com.thezayin.presentation.state

import com.thezayin.domain.model.ResultModel
import com.thezayin.framework.state.State

sealed interface ResultState : State {
    data class ResultUiState(
        val loading: Boolean = false,
        val result: ResultModel? = null,
        val resultNotFound: Boolean? = null,
        val errorDialog: Boolean = false,
        val error: String = "",
    )
}