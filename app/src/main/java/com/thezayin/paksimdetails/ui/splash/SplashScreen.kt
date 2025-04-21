package com.thezayin.paksimdetails.ui.splash

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.thezayin.paksimdetails.R
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.BannerAd
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.splash.component.BottomText
import com.thezayin.paksimdetails.ui.splash.component.ImageHeader
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    navigateToLanguageScreen: () -> Unit,
    navigateToHome: () -> Unit
) {
    val viewModel: SplashViewModel = koinInject()
    val activity = LocalActivity.current as Activity
    val adManager = viewModel.admobManager
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        adManager.loadAd(activity)
    }

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("SplashScreen"))

    GlassComponent()

    LaunchedEffect(Unit) {
        delay(10000)
        adManager.showAd(
            activity = activity,
            showAd = viewModel.remoteConfig.adConfigs.adOnSplashScreen,
            adImpression = {},
            onNext = {
                if (viewModel.isFirstTime) {
                    navigateToLanguageScreen()
                } else {
                    navigateToHome()
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        bottomBar = {
            Column {
                BottomText(modifier = Modifier, text = state.currentSplashText)
                BannerAd(viewModel.remoteConfig.adConfigs.bannerAd)
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