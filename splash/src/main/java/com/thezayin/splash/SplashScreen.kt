package com.thezayin.splash

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.Lifecycle
import com.thezayin.analytics.events.AnalyticsEvent
import com.thezayin.common.component.GlassComponent
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.splash.component.BottomText
import com.thezayin.splash.component.ImageHeader
import com.thezayin.values.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    onNavigate: () -> Unit
) {
    val viewModel: SplashViewModel = koinInject()
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("SplashScreen"))

    val nativeAd = remember { viewModel.nativeAd }
    val showNativeAd =
        remember { mutableStateOf(viewModel.remoteConfig.adConfigs.nativeAdOnHomeScreen) }

    GlassComponent()

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                scope.launch {
                    while (this.isActive) {
                      /*  viewModel.getNativeAd()*/
                        delay(20000L)
                    }
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        delay(4000)
        onNavigate()
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        bottomBar = {
            Column {
                BottomText(modifier = Modifier)
                if (showNativeAd.value) {
                    GoogleNativeAd(
                        modifier = Modifier,
                        style = GoogleNativeAdStyle.Small,
                        nativeAd = nativeAd.value
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ImageHeader(modifier = Modifier.align(Alignment.Center))
        }
    }
}