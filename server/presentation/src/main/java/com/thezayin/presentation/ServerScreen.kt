package com.thezayin.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.thezayin.framework.ads.InterstitialAdStatus
import com.thezayin.framework.ads.interstitialAd
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.presentation.components.ServerScreenContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ServerScreen(
    onServerClick: (String) -> Unit,
    onBackPress: () -> Unit,
    onPremiumClick: () -> Unit
) {
    val viewModel: ServerViewModel = koinInject()
    val state = viewModel.serverUiState.collectAsState().value

    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }
    val showBottomAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnServerScreen) }
    val showLoadingAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnServerLoadingDialog) }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("ServerScreen"))
    var isLoading = remember { mutableStateOf(false) }

    GlassComponent()

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
            callback = { onBackPress() })
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

            else -> {
            }
        }
    }

    state.list?.let {
        ServerScreenContent(
            showBottomAd = showBottomAd,
            nativeAd = nativeAd.value,
            modifier = Modifier,
            showPremium = viewModel.remoteConfig.adConfigs.showPremium,
            list = it,
            onBackClick = {
                activity.interstitialAd(
                    scope = scope,
                    googleManager = viewModel.googleManager,
                    analytics = viewModel.analytics,
                    showAd = viewModel.remoteConfig.adConfigs.adOnBackPress,
                    showLoadingIndicator = { isLoading.value = true },    // Show loading indicator
                    hideLoadingIndicator = { isLoading.value = false },    // Hide loading indicator
                ) { adStatus ->
                    when (adStatus) {
                        is InterstitialAdStatus.AdNotAvailable -> {
                            Log.d("jejeScreen", "Ad not available")
                            onBackPress()
                        }

                        is InterstitialAdStatus.Shown -> {
                            Log.d("jejeScreen", "Ad was shown")
                            onBackPress()
                        }

                        is InterstitialAdStatus.AdAvailable -> {
                            // Optional: Already handled by timeout in interstitialAd
                            // No action needed here
                        }
                    }
                }
            },
            onPremiumClick = {
                onPremiumClick()
            },
            onServerClick = { serv ->
                viewModel.analytics.logEvent(
                    AnalyticsEvent.ServerSelectionEvent(
                        status = serv
                    )
                )
                onServerClick(serv)
            }
        )
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