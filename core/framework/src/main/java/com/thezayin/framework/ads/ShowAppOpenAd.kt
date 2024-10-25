package com.thezayin.framework.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

fun Activity.showAppOpenAd(
    googleManager: GoogleManager,
    analytics: Analytics,
    showAd: Boolean,
    callBack: () -> Unit = {},
): AppOpenAd? {

    if (!showAd) {
        callBack()
        return null
    }

//    val ad = googleManager.createAppOpenAd()
//    ad?.let {
//        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
//            override fun onAdDismissedFullScreenContent() {
//                super.onAdDismissedFullScreenContent()
//                callBack.invoke()
//            }
//
//            override fun onAdImpression() {
//                super.onAdImpression()
//                analytics.logEvent(
//                    AnalyticsEvent.AppOpenAdEvent(
//                        status = "Interstitial_Ad_Impression"
//                    )
//                )
//            }
//
//            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                super.onAdFailedToShowFullScreenContent(p0)
//                callBack.invoke()
//            }
//        }
//        ad.show(this)
//    } ?: callBack.invoke()
    return null
}