package com.thezayin.paksimdetails.ui.history.presentation.state

import com.thezayin.paksimdetails.ui.history.domain.model.HistoryModel

data class HistoryUiState(
    val list: List<HistoryModel>? = null,
    val resultNotFound: Boolean? = null,
    val loading: Boolean = false,
    val errorDialog: Boolean = false,
    val error: String = "",
    val deleteDialogShow: Boolean = false,
)