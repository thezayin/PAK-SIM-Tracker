package com.thezayin.paksimdetails.ui.result.presentation.state

import com.thezayin.paksimdetails.framework.state.State
import com.thezayin.paksimdetails.ui.result.domain.model.ResultModel

sealed interface ResultState : State {
    data class ResultUiState(
        val loading: Boolean = false,
        val result: ResultModel? = null,
        val resultNotFound: Boolean? = null,
        val errorDialog: Boolean = false,
        val error: String = "",
    )
}