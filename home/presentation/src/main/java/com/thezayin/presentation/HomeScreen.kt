package com.thezayin.presentation

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    onPremiumClick: () -> Unit,
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

            else -> {
            }
        }
    }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("HomeScreen"))

    HomeScreenContent(
        modifier = Modifier,
        nativeAd = nativeAd.value,
        showNativeAd = showNativeAd.value,
        result = state.result,
        resultNotFound = state.resultNotFound,
        onPremiumClick = {
            activity.interstitialAd(
                scope = scope,
                analytics = viewModel.analytics,
                googleManager = viewModel.googleManager,
                showAd = viewModel.remoteConfig.adConfigs.adOnPremiumClick
            ) {
                onPremiumClick()
            }
        },
        onMenuClick = {
            activity.interstitialAd(
                scope = scope,
                analytics = viewModel.analytics,
                googleManager = viewModel.googleManager,
                showAd = viewModel.remoteConfig.adConfigs.adOnSettingClick
            ) {
                onMenuClick()
            }
        },
        onServerClick = {
            activity.interstitialAd(
                scope = scope,
                analytics = viewModel.analytics,
                googleManager = viewModel.googleManager,
                showAd = viewModel.remoteConfig.adConfigs.adOnServerClick
            ) {
                onServerClick()
            }
        },
        onSearchClick = {
            activity.interstitialAd(
                scope = scope,
                analytics = viewModel.analytics,
                googleManager = viewModel.googleManager,
                showAd = viewModel.remoteConfig.adConfigs.adOnSearchClick
            ) {
                viewModel.searchNumber(it)
                viewModel.analytics.logEvent(
                    AnalyticsEvent.SearchNumberClick(
                        status = it
                    )
                )
            }
        }
    )
}