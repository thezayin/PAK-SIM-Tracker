package com.thezayin.paksimdetails.ui.premium

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.thezayin.paksimdetails.framework.analytics.events.AnalyticsEvent
import com.thezayin.paksimdetails.ui.common.component.GlassComponent
import com.thezayin.paksimdetails.ui.premium.component.PremiumScreenContent
import org.koin.compose.koinInject

@Composable
fun PremiumScreen(
    onBackClick: () -> Unit
) {
    val viewModel: PremiumViewModel = koinInject()

    val scope = rememberCoroutineScope()

    viewModel.analytics.logEvent(AnalyticsEvent.ScreenViewEvent("PremiumScreen"))

    GlassComponent()


    PremiumScreenContent(
        modifier = Modifier, onBackClick = onBackClick
    )
}