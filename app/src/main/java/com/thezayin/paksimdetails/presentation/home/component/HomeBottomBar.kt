package com.thezayin.paksimdetails.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.nativead.NativeAd
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.paksimdetails.presentation.activities.MainViewModel
import com.thezayin.paksimdetails.presentation.home.component.GetAllServices

@Composable
fun HomeBottomBar(
    modifier: Modifier,
    navigator: DestinationsNavigator,
    nativeAd: NativeAd?,
    mainViewModel: MainViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        if (mainViewModel.remoteConfig.showAdOnHomeScreenNative) {
            GoogleNativeAd(
                modifier = Modifier,
                style = GoogleNativeAdStyle.Small,
                nativeAd = nativeAd
            )
        }
    }
}