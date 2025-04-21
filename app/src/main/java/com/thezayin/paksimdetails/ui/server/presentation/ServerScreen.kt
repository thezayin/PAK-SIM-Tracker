package com.thezayin.paksimdetails.ui.server.presentation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.common.dailogs.ErrorDialog
import com.thezayin.paksimdetails.ui.common.dailogs.LoadingDialog
import com.thezayin.paksimdetails.ui.server.presentation.components.ServerScreenContent
import org.koin.compose.koinInject

@Composable
fun ServerScreen(
    onServerClick: (String) -> Unit, onBackPress: () -> Unit, onPremiumClick: () -> Unit
) {
    val viewModel: ServerViewModel = koinInject()
    val state = viewModel.serverUiState.collectAsState().value
    val adManager = viewModel.adManager
    val activity = LocalActivity.current as Activity

    LaunchedEffect(Unit) {
        adManager.loadAd(activity)
    }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("ServerScreen"))
    var isLoading = remember { mutableStateOf(false) }

    GlassComponent()

    if (state.loading) LoadingDialog()

    if (state.errorDialog) {
        ErrorDialog(
            error = state.error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { onBackPress() })
    }


    state.list?.let {
        ServerScreenContent(
            modifier = Modifier,
            list = it,
            onBackClick = onBackPress,
            onPremiumClick = {
                onPremiumClick()
            },
            onServerClick = { serv ->
                adManager.showAd(
                    activity = activity,
                    showAd = viewModel.remoteConfig.adConfigs.onServerClick,
                    adImpression = {
                        viewModel.analytics.logEvent(
                            AnalyticsEvent.AdImpressionEvent(
                                event = "AdShown", provider = "Interstitial"
                            )
                        )
                    },
                    onNext = {
                        viewModel.analytics.logEvent(
                            AnalyticsEvent.ServerSelectionEvent(
                                status = serv
                            )
                        )
                        onServerClick(serv)
                    })
            })
    }

    LoadingOverlay(visible = isLoading.value)
}

@Composable
fun LoadingOverlay(visible: Boolean) {
    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}