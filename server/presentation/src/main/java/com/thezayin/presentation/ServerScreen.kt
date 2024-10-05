package com.thezayin.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.thezayin.analytics.events.AnalyticsEvent
import com.thezayin.common.component.GlassComponent
import com.thezayin.common.dailogs.ErrorDialog
import com.thezayin.common.dailogs.LoadingDialog
import com.thezayin.framework.ads.interstitialAd
import com.thezayin.framework.ads.showRewardedAd
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
                    showAd = viewModel.remoteConfig.adConfigs.adOnBackPress
                ) {
                    onBackPress()
                }
            },
            onPremiumClick = {
                activity.showRewardedAd(
                    analytics = viewModel.analytics,
                    googleManager = viewModel.googleManager,
                    showAd = viewModel.remoteConfig.adConfigs.adOnPremiumClick
                ) { onPremiumClick() }
            },
            onServerClick = { serv ->
                activity.showRewardedAd(
                    analytics = viewModel.analytics,
                    googleManager = viewModel.googleManager,
                    showAd = viewModel.remoteConfig.adConfigs.onServerClick
                ) {
                    viewModel.analytics.logEvent(
                        AnalyticsEvent.ServerSelectionEvent(
                            status = serv
                        )
                    )
                    onServerClick(serv)
                }
            }
        )
    }
}