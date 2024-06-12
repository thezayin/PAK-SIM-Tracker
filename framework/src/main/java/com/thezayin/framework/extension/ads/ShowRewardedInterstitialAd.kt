package com.thezayin.framework.extension.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.thezayin.ads.GoogleManager

fun Activity.showRewardedInterstitialAd(
    googleManager: GoogleManager,
    boolean: Boolean,
    callBack: (RewardedInterstitialAdStatus) -> Unit
) {
    if (!boolean) {
        callBack(RewardedInterstitialAdStatus.AdNotAvailable)
        return
    }

    val adMob = googleManager.createRewardedInterstitialAd()
    adMob?.apply {
        fullScreenContentCallback = AdmobRewardedInterListener(callBack)
        show(this@showRewardedInterstitialAd) {
            callBack.invoke(RewardedInterstitialAdStatus.UserRewarded)
        }
    }
}

internal class AdmobRewardedInterListener(
    private val callback: (RewardedInterstitialAdStatus) -> Unit,
) : FullScreenContentCallback() {
    private var clicks = 0
    private val vendor = "Google"

    override fun onAdClicked() {
        super.onAdClicked()
        clicks++
    }

    override fun onAdDismissedFullScreenContent() {
        super.onAdDismissedFullScreenContent()
        callback.invoke(RewardedInterstitialAdStatus.Shown(clicks, vendor))
    }

    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
        super.onAdFailedToShowFullScreenContent(p0)
        callback.invoke(RewardedInterstitialAdStatus.AdNotAvailable)
    }
}

sealed class RewardedInterstitialAdStatus {
    data class Shown(
        val clicks: Int,
        val vendor: String,
    ) : RewardedInterstitialAdStatus()

    data object AdNotAvailable : RewardedInterstitialAdStatus()
    data object AdAvailable : RewardedInterstitialAdStatus()
    data object UserRewarded : RewardedInterstitialAdStatus()
}