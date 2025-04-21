package com.thezayin.paksimdetails.ui.result.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.common.dailogs.ErrorDialog
import com.thezayin.paksimdetails.ui.common.dailogs.LoadingDialog
import com.thezayin.paksimdetails.ui.result.presentation.components.ResultScreenContent
import org.koin.compose.koinInject

@Composable
fun ResultScreen(phoneNumber: String, onBackPress: () -> Unit, onPremiumClick: () -> Unit) {
    val viewModel: ResultViewModel = koinInject()
    val state by viewModel.resultUiState.collectAsState()
    GlassComponent()
    LaunchedEffect(Unit) { viewModel.searchNumber(phoneNumber) }
    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("ResultScreen"))
    if (state.loading) LoadingDialog()

    if (state.errorDialog) {
        ErrorDialog(
            error = state.error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { onBackPress() })
    }

    ResultScreenContent(
        modifier = Modifier,
        result = state.result,
        resultNotFound = state.resultNotFound,
        onBackClick = onBackPress,
        onPremiumClick = onPremiumClick
    )
}