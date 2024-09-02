package com.thezayin.premium

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import com.thezayin.analytics.events.AnalyticsEvent
import com.thezayin.common.component.GlassComponent
import com.thezayin.common.component.SetBarColors
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.premium.component.PremiumScreenContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun PremiumScreen(
    onBackClick: () -> Unit
) {
    val viewModel: PremiumViewModel = koinInject()

    val scope = rememberCoroutineScope()
    val nativeAd = remember { viewModel.nativeAd }
    val showAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnPremiumScreen) }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("PremiumScreen"))

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

            else -> {
            }
        }
    }

    PremiumScreenContent(
        modifier = Modifier,
        showAd = showAd.value,
        nativeAd = nativeAd.value,
        onBackClick = onBackClick
    )
}