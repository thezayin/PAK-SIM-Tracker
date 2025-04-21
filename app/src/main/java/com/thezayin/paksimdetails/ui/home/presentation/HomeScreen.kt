package com.thezayin.paksimdetails.ui.home.presentation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.common.dailogs.ErrorDialog
import com.thezayin.paksimdetails.ui.common.dailogs.LoadingDialog
import com.thezayin.paksimdetails.ui.home.presentation.component.HomeScreenContent
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onHistoryClick: () -> Unit,
    onMenuClick: () -> Unit,
    onServerClick: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    val viewModel: HomeViewModel = koinInject()
    val state by viewModel.resultUiState.collectAsState()
    val activity = LocalActivity.current as Activity

    val interstitialAdManager = viewModel.interstitialAdManager
    val rewardedAdManager = viewModel.rewardedAdManager

    LaunchedEffect(Unit) {
        interstitialAdManager.loadAd(activity)
        rewardedAdManager.loadAd(activity)
    }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("HomeScreen"))
    var isLoading = remember { mutableStateOf(false) }
    GlassComponent()


    if (state.loading) LoadingDialog()


    if (state.errorDialog) {
        ErrorDialog(
            error = state.error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { })
    }


    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("HomeScreen"))

    HomeScreenContent(
        modifier = Modifier,
        onHistoryClick = {
            rewardedAdManager.showAd(
                showAd = viewModel.remoteConfig.adConfigs.adOnHistoryClick,
                activity = activity,
                onNext = {
                    onHistoryClick()
                },
            )
        },
        onMenuClick = {
            onMenuClick()
        },
        onServerClick = {
            interstitialAdManager.showAd(
                showAd = viewModel.remoteConfig.adConfigs.adOnServerOptionClick,
                activity = activity,
                onNext = {
                    onServerClick()
                },
                adImpression = {
                    viewModel.analytics.logEvent(
                        AnalyticsEvent.AdImpressionEvent(
                            event = "ServerClickInterstitial",
                            provider = "AdMob",
                        )
                    )
                }
            )
        },
        onSearchClick = { res ->
            rewardedAdManager.showAd(
                showAd = viewModel.remoteConfig.adConfigs.adOnSearchClick,
                activity = activity,
                onNext = {
                    viewModel.analytics.logEvent(
                        AnalyticsEvent.SearchNumberClick(
                            status = res
                        )
                    )
                    onSearchClick(res)
                },
                adImpression = {
                    viewModel.analytics.logEvent(
                        AnalyticsEvent.AdImpressionEvent(
                            event = "SearchClickRewarded",
                            provider = "AdMob",
                        )
                    )
                }
            )
        }
    )
    LoadingsOverlay(visible = isLoading.value)
}

@Composable
fun LoadingsOverlay(visible: Boolean) {
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