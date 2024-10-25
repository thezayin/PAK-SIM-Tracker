package com.thezayin.web

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
import com.thezayin.common.component.SetBarColors
import com.thezayin.common.dailogs.ErrorDialog
import com.thezayin.common.dailogs.LoadingDialog
import com.thezayin.framework.ads.interstitialAd
import com.thezayin.framework.ads.showRewardedAd
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.web.component.WebScreenContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun WebScreen(
    url: String,
    onBackPress: () -> Unit,
    onPremiumClick: () -> Unit,
) {
    val viewModel: WebViewModel = koinInject()
    val state = viewModel.webUiState.collectAsState().value

    val showBottomAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnWebScreen) }
    val showLoadingAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnWebLoadingDialog) }

    val backEnabled = remember { mutableStateOf(false) }
    val infoDialog = remember { mutableStateOf(false) }

    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("WebScreen"))

    GlassComponent()
    SetBarColors()

    if (state.loading) {
        LoadingDialog(showAd = showLoadingAd.value, nativeAd = nativeAd.value, ad = {
            GoogleNativeAd(
                modifier = Modifier,
                style = GoogleNativeAdStyle.Small,
                nativeAd = nativeAd.value
            )
        })
    }

    if (state.errorDialog) {
        ErrorDialog(error = state.error,
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

    if (state.errorDialog) {
        ErrorDialog(error = state.error,
            showDialog = { viewModel.hideErrorDialog() },
            callback = { onBackPress() })
    }

    WebScreenContent(modifier = Modifier,
        url = url,
        showBottomAd = showBottomAd.value,
        nativeAd = nativeAd,
        backEnabled = backEnabled,
        showPremium = viewModel.remoteConfig.adConfigs.showPremium,
        infoDialog = infoDialog,
        showLoading = { viewModel.showLoading() },
        hideLoading = { viewModel.hideLoading() },
        onBackClick = {
//            activity.interstitialAd(
//                scope = scope,
//                analytics = viewModel.analytics,
//                googleManager = viewModel.googleManager,
//                showAd = viewModel.remoteConfig.adConfigs.adOnBackPress
//            ) { onBackPress() }
        },
        onPremiumClick = {
//            activity.showRewardedAd(
//                analytics = viewModel.analytics,
//                googleManager = viewModel.googleManager,
//                showAd = viewModel.remoteConfig.adConfigs.adOnPremiumClick
//            ) { onPremiumClick() }
        })
}