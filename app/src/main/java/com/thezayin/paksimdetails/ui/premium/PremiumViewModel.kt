package com.thezayin.paksimdetails.ui.premium

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.paksimdetails.framework.analytics.analytics.Analytics
import com.thezayin.paksimdetails.framework.remote.RemoteConfig

class PremiumViewModel(
    val remoteConfig: RemoteConfig,
    val analytics: Analytics
) : ViewModel() {
    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

}