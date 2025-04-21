package com.thezayin.paksimdetails.ui.history.presentation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.common.dailogs.ErrorDialog
import com.thezayin.paksimdetails.ui.common.dailogs.LoadingDialog
import com.thezayin.paksimdetails.ui.history.presentation.component.RecentScreenContent
import org.koin.compose.koinInject

@Composable
fun HistoryScreen(
    onBackPress: () -> Unit
) {
    val viewModel: HistoryViewModel = koinInject()
    val state = viewModel.historyUiState.collectAsState().value
    val activity = LocalActivity.current as Activity
    val historyNotFound = state.resultNotFound
    val list = state.list
    val error = state.error
    val loading = state.loading
    val errorDialog = state.errorDialog
    val adManager = viewModel.rewardedAdManager
    GlassComponent()
    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("HistoryScreen"))
    if (loading) LoadingDialog()

    if (errorDialog) {
        ErrorDialog(
            error = error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { onBackPress() })
    }

    RecentScreenContent(
        historyNotFound = historyNotFound,
        list = list,
        onBackClick = onBackPress,
        onDeleteClick = {
            adManager.showAd(
                activity = activity,
                adImpression = {},
                showAd = viewModel.remoteConfig.adConfigs.adOnDeleteClick,
                onNext = {
                    viewModel.deleteSearchHistory()
                }
            )
        },
    )
}