package com.thezayin.paksimdetails.ui.setting

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.setting.component.SettingScreenContent
import org.koin.compose.koinInject

@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    onPremiumClick: () -> Unit
) {
    val viewModel: SettingViewModel = koinInject()
    val activity = LocalActivity.current as Activity
    val adManager = viewModel.adManager
    adManager.loadAd(activity)
    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("SettingScreen"))
    GlassComponent()
    SettingScreenContent(
        modifier = Modifier,
        onBackClick = onBackClick,
        onPremiumClick =  onPremiumClick
    )
}