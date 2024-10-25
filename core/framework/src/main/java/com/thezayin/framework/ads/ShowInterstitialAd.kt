package com.thezayin.framework.ads

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.thezayin.ads.GoogleManager
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.analytics.events.AnalyticsEvent

fun showInterstitialAd(
    activity: Activity,
    manager: GoogleManager,
    boolean: Boolean,
    analytics: Analytics,
    callBack: (InterstitialAdStatus) -> Unit = {}
) {
    if (!boolean) {
        Log.d("jejeInterstitialAdFun", "Ad request not allowed, returning.")
        callBack(InterstitialAdStatus.AdNotAvailable)
        return
    }

    manager.createInterstitialAd(
        onLoading = {
            Log.d("jejeInterstitialAdFun", "Ad is loading")
            callBack(InterstitialAdStatus.AdAvailable)
        },
        onAdReady = { adMob ->
            if (adMob != null) {
                Log.d("jejeInterstitialAdFun", "Ad loaded successfully, showing ad")
                // Set up the ad callbacks
                adMob.fullScreenContentCallback = AdmobInterListener(callBack, analytics)
                adMob.show(activity)
            } else {
                Log.d("jejeInterstitialAdFun", "Ad failed to load")
                callBack(InterstitialAdStatus.AdNotAvailable)
            }
        }
    )
}

internal class AdmobInterListener(
    private val callback: (InterstitialAdStatus) -> Unit,
    private val analytics: Analytics
) : FullScreenContentCallback() {
    private var clicks = 0

    override fun onAdClicked() {
        super.onAdClicked()
        clicks++
    }

    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        Log.d("jejAdmobInterListener", "Ad dismissed")
        callback.invoke(InterstitialAdStatus.Shown(clicks, "Google"))
    }

    override fun onAdImpression() {
        super.onAdImpression()
        Log.d("jejAdmobInterListener", "Ad impression logged")
        analytics.logEvent(
            AnalyticsEvent.InterstitialAdEvent(
                status = "Interstitial_Ad_Impression"
            )
        )
    }

    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        super.onAdFailedToShowFullScreenContent(adError)
        Log.d("jejAdmobInterListener", "Ad failed to show: ${adError.message}")
        callback.invoke(InterstitialAdStatus.AdNotAvailable)
    }

    override fun onAdShowedFullScreenContent() {
        super.onAdShowedFullScreenContent()
        Log.d("jejAdmobInterListener", "Ad showed successfully")
    }
}

sealed class InterstitialAdStatus {
    data class Shown(
        val clicks: Int,
        val vendor: String,
    ) : InterstitialAdStatus()

    data object AdNotAvailable : InterstitialAdStatus()
    data object AdAvailable : InterstitialAdStatus()
}