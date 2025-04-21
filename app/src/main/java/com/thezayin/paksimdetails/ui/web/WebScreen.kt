package com.thezayin.paksimdetails.ui.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.common.dailogs.ErrorDialog
import com.thezayin.paksimdetails.ui.common.dailogs.LoadingDialog
import com.thezayin.paksimdetails.ui.web.component.WebScreenContent
import org.koin.compose.koinInject

@Composable
fun WebScreen(
    url: String,
    onBackPress: () -> Unit,
    onPremiumClick: () -> Unit,
) {
    val viewModel: WebViewModel = koinInject()
    val state = viewModel.webUiState.collectAsState().value

    val backEnabled = remember { mutableStateOf(false) }
    val infoDialog = remember { mutableStateOf(false) }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("WebScreen"))

    GlassComponent()

    if (state.loading) LoadingDialog()

    if (state.errorDialog) {
        ErrorDialog(
            error = state.error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { onBackPress() })
    }


    if (state.errorDialog) {
        ErrorDialog(
            error = state.error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { onBackPress() })
    }

    WebScreenContent(
        modifier = Modifier,
        url = url,
        backEnabled = backEnabled,
        infoDialog = infoDialog,
        showLoading = { viewModel.showLoading() },
        hideLoading = { viewModel.hideLoading() },
        onBackClick = onBackPress,
        onPremiumClick = onPremiumClick
    )
}