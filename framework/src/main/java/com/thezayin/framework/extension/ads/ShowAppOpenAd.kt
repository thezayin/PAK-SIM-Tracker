package com.thezayin.framework.extension.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.thezayin.ads.GoogleManager

fun showAppOpenAd(
    activity: Activity,
    googleManager: GoogleManager,
    showAd: Boolean = true,
    callBack: (() -> Unit)? = null,
): AppOpenAd? {
    if (!showAd) {
        callBack?.invoke()
        return null
    }
    val ad = googleManager.createAppOpenAd()
    ad?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            callBack?.invoke()
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            callBack?.invoke()
        }
    }
    ad?.show(activity)
    return ad
}