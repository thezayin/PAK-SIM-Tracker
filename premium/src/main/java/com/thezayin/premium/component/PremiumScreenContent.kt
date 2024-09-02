package com.thezayin.premium.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.ssp

@Composable
fun PremiumScreenContent(
    modifier: Modifier,
    onBackClick: () -> Unit,
    showAd: Boolean?,
    nativeAd: NativeAd?
) {
    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            PremiumTopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
            )
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                "Coming soon", color = colorResource(id = R.color.black),
                fontSize = 17.ssp,
                fontFamily = FontFamily(Font(R.font.abeezee_italic)),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}