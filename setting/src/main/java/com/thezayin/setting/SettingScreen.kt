package com.thezayin.setting

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.thezayin.analytics.events.AnalyticsEvent
import com.thezayin.common.component.GlassComponent
import com.thezayin.common.component.SetBarColors
import com.thezayin.framework.ads.interstitialAd
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.setting.component.SettingScreenContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    onPremiumClick: () -> Unit
) {
    val viewModel: SettingViewModel = koinInject()

    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }
    val showBottomAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnSettingScreen) }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("SettingScreen"))

    GlassComponent()
    SetBarColors()

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

    SettingScreenContent(
        modifier = Modifier,
        showAd = showBottomAd.value,
        nativeAd = nativeAd.value,
        onBackClick = {
            activity.interstitialAd(
                scope = scope,
                analytics = viewModel.analytics,
                googleManager = viewModel.googleManager,
                showAd = viewModel.remoteConfig.adConfigs.adOnBackPress
            ) { onBackClick() }
        },
        onPremiumClick = {
            activity.interstitialAd(
                scope = scope,
                analytics = viewModel.analytics,
                googleManager = viewModel.googleManager,
                showAd = viewModel.remoteConfig.adConfigs.adOnPremiumClick
            ) { onPremiumClick() }
        }
    )
}