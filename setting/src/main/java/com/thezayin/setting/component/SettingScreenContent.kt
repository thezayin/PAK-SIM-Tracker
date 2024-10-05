package com.thezayin.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.common.topbar.TopBar
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SettingScreenContent(
    modifier: Modifier,
    showPremium: Boolean,
    onBackClick: () -> Unit,
    onPremiumClick: () -> Unit,
    showAd: Boolean?,
    nativeAd: NativeAd?,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = com.thezayin.values.R.color.background),
        topBar = {
            Column {
                TopBar(
                    modifier = Modifier,
                    showPremium = showPremium,
                    onBackClick = onBackClick,
                    onPremiumClick = onPremiumClick,
                    screenName = "Settings"
                )
                if (showAd == true) {
                    GoogleNativeAd(
                        modifier = Modifier,
                        style = GoogleNativeAdStyle.Small,
                        nativeAd = nativeAd
                    )
                }
            }
        },
        bottomBar = {
            if (showAd == true) {
                GoogleNativeAd(
                    modifier = Modifier,
                    style = GoogleNativeAdStyle.Small,
                    nativeAd = nativeAd
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = 60.sdp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            SettingOptionsList()
        }
    }
}