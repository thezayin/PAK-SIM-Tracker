package com.thezayin.presentation

import android.app.Activity
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.thezayin.analytics.events.AnalyticsEvent
import com.thezayin.common.component.GlassComponent
import com.thezayin.common.dailogs.ErrorDialog
import com.thezayin.common.dailogs.LoadingDialog
import com.thezayin.framework.ads.RewardedAdStatus
import com.thezayin.framework.ads.showRewardedAd
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.presentation.component.HomeScreenContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onHistoryClick: () -> Unit,
    onMenuClick: () -> Unit,
    onServerClick: () -> Unit,
) {
    val viewModel: HomeViewModel = koinInject()
    val state by viewModel.resultUiState.collectAsState()
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }
    val showNativeAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnHomeScreen) }
    val showLoadingAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnResultLoadingDialog) }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("HomeScreen"))
    var isLoading = remember { mutableStateOf(false) }
    GlassComponent()

    LaunchedEffect(state.resultNotFound) {
        if (state.resultNotFound == false) {
            Toast.makeText(activity, "Result not found", Toast.LENGTH_SHORT).show()
        }
    }

    if (state.loading) {
        LoadingDialog(
            showAd = showLoadingAd.value,
            nativeAd = nativeAd.value,
            ad = {
                GoogleNativeAd(
                    modifier = Modifier,
                    style = GoogleNativeAdStyle.Small,
                    nativeAd = nativeAd.value
                )
            }
        )
    }

    if (state.errorDialog) {
        ErrorDialog(
            error = state.error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { })
    }

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                scope.launch {
                    while (this.isActive) {
                        viewModel.getNativeAd()
                        delay(20000L)
                    }
                }
            }

            else -> {}
        }
    }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("HomeScreen"))

    HomeScreenContent(
        modifier = Modifier,
        nativeAd = nativeAd.value,
        showNativeAd = showNativeAd.value,
        result = state.result,
        showPremium = viewModel.remoteConfig.adConfigs.showPremium,
        resultNotFound = state.resultNotFound,
        onHistoryClick = {
            activity.showRewardedAd(
                scope = scope,
                googleManager = viewModel.googleManager,
                analytics = viewModel.analytics,
                showAd = viewModel.remoteConfig.adConfigs.adOnPremiumClick,
                showLoadingIndicator = { isLoading.value = true },    // Show loading indicator
                hideLoadingIndicator = { isLoading.value = false },    // Hide loading indicator
            ) { adStatus ->
                when (adStatus) {
                    is RewardedAdStatus.AdNotAvailable -> {
                        Log.d("jejeServerScreen", "Rewarded Ad not available")
                        onHistoryClick()
                    }

                    is RewardedAdStatus.UserRewarded -> {
                        Log.d("jejeServerScreen", "User was rewarded by Rewarded Ad")
                        viewModel.analytics.logEvent(
                            AnalyticsEvent.ServerSelectionEvent(
                                status = "Premium"
                            )
                        )
                        onHistoryClick()
                    }

                    is RewardedAdStatus.AdLoading -> {
                        // Already handled by loading indicator
                    }

                    is RewardedAdStatus.AdAvailable -> {
                        // Optional: Already handled by timeout in showRewardedAd
                    }

                    is RewardedAdStatus.UserCancelled -> {
                        Log.d("jejeServerScreen", "User cancelled the Rewarded Ad")
                        // Optionally handle user cancellation
                    }
                }
            }
        },
        onMenuClick = {

                onMenuClick()
        },
        onServerClick = {
                onServerClick()
        },
        onSearchClick = { res ->
                viewModel.searchNumber(res)
                viewModel.analytics.logEvent(
                    AnalyticsEvent.SearchNumberClick(
                        status = res
                    )
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